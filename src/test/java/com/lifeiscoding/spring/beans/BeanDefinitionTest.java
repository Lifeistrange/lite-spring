package com.lifeiscoding.spring.beans;

import com.lifeiscoding.spring.beans.factory.config.RuntimeBeanReference;
import com.lifeiscoding.spring.beans.factory.config.TypedStringValue;
import com.lifeiscoding.spring.beans.factory.support.DefaultBeanFactory;
import com.lifeiscoding.spring.beans.factory.xml.XMLBeanDefinitionReader;
import com.lifeiscoding.spring.core.io.ClassPathResource;
import com.lifeiscoding.spring.core.io.Resource;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class BeanDefinitionTest {

    @Test
    public void testGetBeanDefinition() {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XMLBeanDefinitionReader reader = new XMLBeanDefinitionReader(factory);

        reader.loadBeanDefinition(new ClassPathResource("petstore-v2.xml"));

        BeanDefinition bd = factory.getBeanDefinition("petStore");

        List<PropertyValue> pvs = bd.getPropertyValues();

        Assert.assertEquals(pvs.size(), 5);
        {
            PropertyValue pv = this.getPropertyValues(pvs, "accountDao");

            Assert.assertNotNull(pv);

            Assert.assertTrue(pv.getValue() instanceof RuntimeBeanReference);
        }

        {
            PropertyValue pv = this.getPropertyValues(pvs, "itemDao");

            Assert.assertNotNull(pv);

            Assert.assertTrue(pv.getValue() instanceof RuntimeBeanReference);
        }
    }

    private PropertyValue getPropertyValues(List<PropertyValue> pvs, String propertyName) {
        for (PropertyValue pv : pvs) {
            if (pv.getName().equals(propertyName)) {
                return pv;
            }
        }
        return null;
    }

    @Test
    public void testConstructorArgument() {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XMLBeanDefinitionReader reader = new XMLBeanDefinitionReader(factory);
        Resource resource = new ClassPathResource("petstore-v3.xml");
        reader.loadBeanDefinition(resource);

        BeanDefinition bd = factory.getBeanDefinition("petStore");
        Assert.assertEquals("com.lifeiscoding.spring.test.service.PetStoreService", bd.getBeanClassName());

        ConstructorArgument args = bd.getConstructorArgument();
        List<ConstructorArgument.ValueHolder> valueHolders = args.getArgumentValues();

        RuntimeBeanReference ref1 = (RuntimeBeanReference) valueHolders.get(0).getValue();
        Assert.assertEquals("accountDao", ref1.getBeanName());
        RuntimeBeanReference ref2 = (RuntimeBeanReference) valueHolders.get(1).getValue();
        Assert.assertEquals("itemDao", ref2.getBeanName());

        TypedStringValue stringValue = (TypedStringValue) valueHolders.get(2).getValue();
        Assert.assertEquals("1", stringValue.getAttributeValue());

    }
}
