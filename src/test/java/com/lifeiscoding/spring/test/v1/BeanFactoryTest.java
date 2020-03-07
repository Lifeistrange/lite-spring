package com.lifeiscoding.spring.test.v1;

import com.lifeiscoding.spring.beans.BeanDefinition;
import com.lifeiscoding.spring.beans.factory.BeanCreationException;
import com.lifeiscoding.spring.beans.factory.BeanDefinitionStoreException;
import com.lifeiscoding.spring.beans.factory.support.DefaultBeanFactory;
import com.lifeiscoding.spring.beans.factory.BeanFactory;
import com.lifeiscoding.spring.service.v1.PetStoreService;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class BeanFactoryTest {

    @Test
    public void testGetBean() {
        BeanFactory factory = new DefaultBeanFactory("petstore-v1.xml");
        BeanDefinition bd = factory.getBeanDefinition("petStore");

        assertEquals("com.lifeiscoding.spring.service.v1.PetStoreService", bd.getBeanClassName());

        PetStoreService petStore = (PetStoreService) factory.getBean("petStore");

        assertNotNull(petStore);
    }

    @Test
    public void testInvilidBean() {
        BeanFactory factory = new DefaultBeanFactory("petstore-v1.xml");
        try {
            factory.getBean("invalidBean");
        } catch (BeanCreationException e) {
            return;
        }
        Assert.fail("except BeanCreationException");
    }

    @Test
    public void testInvalidXML() {
        try {
            new DefaultBeanFactory("xxx.xml");
        } catch (BeanDefinitionStoreException e) {
            return;
        }

        Assert.fail("except BeanDefinitionStoreException");
    }

}
