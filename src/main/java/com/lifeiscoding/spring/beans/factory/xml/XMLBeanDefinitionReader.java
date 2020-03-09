package com.lifeiscoding.spring.beans.factory.xml;

import com.lifeiscoding.spring.beans.BeanDefinition;
import com.lifeiscoding.spring.beans.factory.BeanDefinitionStoreException;
import com.lifeiscoding.spring.beans.factory.support.BeanDefinitionRegistry;
import com.lifeiscoding.spring.beans.factory.support.GenericBeanDefinition;
import com.lifeiscoding.spring.core.io.Resource;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class XMLBeanDefinitionReader {
    public static final String ID_ATTRABUTE = "id";
    public static final String CLASS_ATTRABUTE = "class";
    public static final String SCOPE_ATTRABUTE = "scope";

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
                String id = ele.attributeValue(ID_ATTRABUTE);
                String beanClassName = ele.attributeValue(CLASS_ATTRABUTE);
                String scope = ele.attributeValue(SCOPE_ATTRABUTE);
                BeanDefinition bd = new GenericBeanDefinition(id, beanClassName);
                if (scope != null) {
                    bd.setScope(scope);
                }
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
}
