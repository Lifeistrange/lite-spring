package com.lifeiscoding.spring.beans;

import com.lifeiscoding.spring.beans.factory.BeanCreationException;
import com.lifeiscoding.spring.beans.factory.BeanDefinitionStoreException;
import com.lifeiscoding.spring.beans.factory.support.DefaultBeanFactory;
import com.lifeiscoding.spring.beans.factory.xml.XMLBeanDefinitionReader;
import com.lifeiscoding.spring.core.io.ClassPathResource;
import com.lifeiscoding.test.service.PetStoreService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BeanFactoryTest {

    private DefaultBeanFactory factory;
    private XMLBeanDefinitionReader reader;

    @Before
    public void setUp() {
        this.factory = new DefaultBeanFactory();
        this.reader = new XMLBeanDefinitionReader(factory);
    }

    @Test
    public void testGetBean() {
        reader.loadBeanDefinition(new ClassPathResource("petstore-v1.xml"));
        BeanDefinition bd = factory.getBeanDefinition("petStore");

        assertTrue(bd.isSingleton());
        assertFalse(bd.isPrototype());
        assertEquals(BeanDefinition.SCOPE.DEFAULT, bd.getScope());


        assertEquals("com.lifeiscoding.test.service.v1.PetStoreService", bd.getBeanClassName());

        PetStoreService petStore = (PetStoreService) factory.getBean("petStore");

        assertNotNull(petStore);

        PetStoreService petStore2 = (PetStoreService) factory.getBean("petStore");

        assertEquals(petStore, petStore2);
    }

    @Test
    public void testInvilidBean() {
        reader.loadBeanDefinition(new ClassPathResource("petstore-v1.xml"));
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
            reader.loadBeanDefinition(new ClassPathResource("xxx.xml"));
        } catch (BeanDefinitionStoreException e) {
            return;
        }

        Assert.fail("except BeanDefinitionStoreException");
    }

}
