package com.lifeiscoding.spring.context;

import com.lifeiscoding.spring.context.support.ClassPathXmlApplicationContext;
import com.lifeiscoding.spring.test.service.PetStoreService;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ApplicationContextTest {

    @Test
    public void testGetBean() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v1.xml");
        PetStoreService petStore = (PetStoreService) ctx.getBean("petStore");
        Assert.assertNotNull(petStore);
    }

    @Test
    public void testGetBeanProperty() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v2.xml");
        PetStoreService petStoreService = (PetStoreService) ctx.getBean("petStore");

        assertNotNull(petStoreService.getAccountDao());
        assertNotNull(petStoreService.getItemDao());
    }
}
