package com.lifeiscoding.spring.test.v1;

import com.lifeiscoding.spring.context.ApplicationContext;
import com.lifeiscoding.spring.context.support.ClassPathXmlApplicationContext;
import com.lifeiscoding.spring.service.v1.PetStoreService;
import org.junit.Assert;
import org.junit.Test;

public class ApplicationContextTest {

    @Test
    public void testGetBean() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v1.xml");
        PetStoreService petStore = (PetStoreService)ctx.getBean("petStore");
        Assert.assertNotNull(petStore);
    }
}
