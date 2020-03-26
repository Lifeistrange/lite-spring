package com.lifeiscoding.spring.beans.factory.xml;

import com.lifeiscoding.spring.beans.BeanDefinition;
import com.lifeiscoding.spring.beans.ConstructorArgument;
import com.lifeiscoding.spring.beans.PropertyValue;
import com.lifeiscoding.spring.beans.factory.BeanDefinitionStoreException;
import com.lifeiscoding.spring.beans.factory.config.RuntimeBeanReference;
import com.lifeiscoding.spring.beans.factory.config.TypedStringValue;
import com.lifeiscoding.spring.beans.factory.support.BeanDefinitionRegistry;
import com.lifeiscoding.spring.beans.factory.support.GenericBeanDefinition;
import com.lifeiscoding.spring.context.annotation.ClassPathBeanDefinitionScanner;
import com.lifeiscoding.spring.core.Resource;
import com.lifeiscoding.spring.util.StringUtils;
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
    private static final String CONSTRUCTOR_ARG_ELEMENT = "constructor-arg";
    public static final String REF_ATTRIBUTE = "ref";
    public static final String VALUE_ATTRIBUTE = "value";
    public static final String NAME_ATTRIBUTE = "name";
    private static final String TYPE_ATTRIBUTE = "type";
    public static final String BEANS_NAMESPACE_URI = "http://www.springframework.org/schema/beans";
    public static final String CONTEXT_NAMESPACE_URI = "http://www.springframework.org/schema/context";
    private static final String BASE_PACKAGE_ATTRIBUTE = "base-package";


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

                String namespaceUri = ele.getNamespaceURI();

                if (this.isDefaultNamespace(namespaceUri)) {
                    parseDefautlElement(ele);
                } else if (this.isContextNamespace(namespaceUri)) {
                    parseComponentElement(ele);
                }

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

    private void parseComponentElement(Element ele) {
        String basePackages = ele.attributeValue(BASE_PACKAGE_ATTRIBUTE);
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(beanDefinitionRegistry);
        scanner.doScan(basePackages);
    }

    private void parseDefautlElement(Element ele) {
        String id = ele.attributeValue(ID_ATTRIBUTE);
        String beanClassName = ele.attributeValue(CLASS_ATTRIBUTE);
        String scope = ele.attributeValue(SCOPE_ATTRIBUTE);
        BeanDefinition bd = new GenericBeanDefinition(id, beanClassName);
        if (scope != null) {
            bd.setScope(scope);
        }
        parseConstructorArgElements(ele, bd);
        parsePropertyElement(ele, bd);
        this.beanDefinitionRegistry.registerBeanDefinition(id, bd);
    }

    public boolean isDefaultNamespace(String namespaceUri) {
        return (!StringUtils.hasLength(namespaceUri) || BEANS_NAMESPACE_URI.equals(namespaceUri));
    }

    public boolean isContextNamespace(String namespaceUri) {
        return (!StringUtils.hasLength(namespaceUri) || CONTEXT_NAMESPACE_URI.equals(namespaceUri));
    }

    private void parseConstructorArgElements(Element beanEle, BeanDefinition bd) {
        Iterator<Element> iterator = beanEle.elementIterator(CONSTRUCTOR_ARG_ELEMENT);
        while (iterator.hasNext()) {
            Element ele = iterator.next();
            parseConstructorArgElement(ele, bd);
        }
    }

    private void parseConstructorArgElement(Element ele, BeanDefinition bd) {
        String typeAttr = ele.attributeValue(TYPE_ATTRIBUTE);
        String nameAttr = ele.attributeValue(NAME_ATTRIBUTE);

        Object value = parsePropertyValue(ele, bd, null);
        ConstructorArgument.ValueHolder valueHolder = new ConstructorArgument.ValueHolder(value);
        if (StringUtils.hasLength(typeAttr)) {
            valueHolder.setType(typeAttr);
        }
        if (StringUtils.hasLength(nameAttr)) {
            valueHolder.setName(nameAttr);
        }

        bd.getConstructorArgument().addArgumentValue(valueHolder);
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
