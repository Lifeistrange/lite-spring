package com.lifeiscoding.spring.beans;

import com.lifeiscoding.spring.beans.factory.config.RuntimeBeanReference;
import com.lifeiscoding.spring.beans.factory.support.DefaultBeanFactory;
import com.lifeiscoding.spring.beans.factory.xml.XMLBeanDefinitionReader;
import com.lifeiscoding.spring.core.io.ClassPathResource;
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

        Assert.assertEquals(pvs.size(), 2);
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
}
