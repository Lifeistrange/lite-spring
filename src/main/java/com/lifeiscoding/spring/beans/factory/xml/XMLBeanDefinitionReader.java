package com.lifeiscoding.spring.beans.factory.xml;

import com.lifeiscoding.spring.beans.BeanDefinition;
import com.lifeiscoding.spring.beans.PropertyValue;
import com.lifeiscoding.spring.beans.factory.BeanCreationException;
import com.lifeiscoding.spring.beans.factory.BeanDefinitionStoreException;
import com.lifeiscoding.spring.beans.factory.config.RuntimeBeanReference;
import com.lifeiscoding.spring.beans.factory.config.TypedStringValue;
import com.lifeiscoding.spring.beans.factory.support.BeanDefinitionRegistry;
import com.lifeiscoding.spring.beans.factory.support.GenericBeanDefinition;
import com.lifeiscoding.spring.core.io.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class XMLBeanDefinitionReader {

    private final Log logger = LogFactory.getLog(getClass());

    public static final String ID_ATTRIBUTE = "id";
    public static final String CLASS_ATTRIBUTE = "class";
    public static final String SCOPE_ATTRIBUTE = "scope";
    public static final String PROPERTY_ATTRIBUTE = "property";
    public static final String REF_ATTRIBUTE = "ref";
    public static final String VALUE_ATTRIBUTE = "value";
    public static final String NAME_ATTRIBUTE = "name";

    private BeanDefinitionRegistry beanDefinitionRegistry;

    public XMLBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    public void loadBeanDefinition(Resource resource) {
        InputStream is = null;
        try {
            is = resource.getInputStream();
            SAXReader reader = new SAXReader();
            Document doc = reader.read(is);

            Element root = doc.getRootElement();
            Iterator<Element> iter = root.elementIterator();
            while (iter.hasNext()) {
                Element ele = (Element) iter.next();
                String id = ele.attributeValue(ID_ATTRIBUTE);
                String beanClassName = ele.attributeValue(CLASS_ATTRIBUTE);
                String scope = ele.attributeValue(SCOPE_ATTRIBUTE);
                BeanDefinition bd = new GenericBeanDefinition(id, beanClassName);
                if (scope != null) {
                    bd.setScope(scope);
                }
                parsePropertyElement(ele, bd);
                this.beanDefinitionRegistry.registerBeanDefinition(id, bd);
            }
        } catch (Exception e) {
            throw new BeanDefinitionStoreException("IOException parsing XML document", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void parsePropertyElement(Element beanElem, BeanDefinition bd) {
        Iterator<Element> iter = beanElem.elementIterator(PROPERTY_ATTRIBUTE);
        while (iter.hasNext()) {
            Element ele = iter.next();
            String propertyName = ele.attributeValue(NAME_ATTRIBUTE);
            if (propertyName.isEmpty()) {
                logger.fatal("Tag 'property' must have 'name' attribute");
                return;
            }

            Object val = parsePropertyValue(ele, bd, propertyName);
            PropertyValue pv = new PropertyValue(propertyName, val);

            bd.getPropertyValues().add(pv);
        }
    }

    private Object parsePropertyValue(Element ele, BeanDefinition bd, String propertyName) {
        String elementName = (propertyName != null) ? "<property> element for property '" + propertyName + "'":
                                                    "<constructor-arg> element";

        boolean hasRefAttribute = (ele.attribute(REF_ATTRIBUTE) != null);
        boolean hasValueAttribute = (ele.attribute(VALUE_ATTRIBUTE) != null);

        if (hasRefAttribute) {
            String refName = ele.attributeValue(REF_ATTRIBUTE);
            if (refName.isEmpty()) {
                logger.error(elementName + "contains empty 'ref' attribute");
            }
            return new RuntimeBeanReference(refName);
        }

        if (hasValueAttribute) {
            return new TypedStringValue(ele.attributeValue(VALUE_ATTRIBUTE));
        }

        throw new RuntimeException(elementName + "must specify a ref or value");
    }


}
