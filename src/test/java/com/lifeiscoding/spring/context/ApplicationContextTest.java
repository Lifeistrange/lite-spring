package com.lifeiscoding.spring.context;

import com.lifeiscoding.spring.context.support.ClassPathXmlApplicationContext;
import com.lifeiscoding.spring.test.dao.AccountDao;
import com.lifeiscoding.spring.test.dao.ItemDao;
import com.lifeiscoding.spring.test.service.PetStoreService;
import com.lifeiscoding.spring.test.util.MessageTracker;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ApplicationContextTest {

    @Test
    public void testGetBeanV1() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v1.xml");
        PetStoreService petStore = (PetStoreService) ctx.getBean("petStore");
        Assert.assertNotNull(petStore);
    }

    @Test
    public void testGetBeanPropertyV2() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v2.xml");
        PetStoreService petStoreService = (PetStoreService) ctx.getBean("petStore");

        assertNotNull(petStoreService.getAccountDao());
        assertNotNull(petStoreService.getItemDao());
        assertNotNull(petStoreService.getOwner());

        assertTrue(petStoreService.getAccountDao() instanceof AccountDao);
        assertTrue(petStoreService.getItemDao() instanceof ItemDao);
        assertEquals("test", petStoreService.getOwner());
        assertEquals(2, petStoreService.getVersion());
        assertEquals(true, petStoreService.getaSwitch());
    }

    @Test
    public void testGetBeanPropertyV3() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v3.xml");
        PetStoreService petStoreService = (PetStoreService) ctx.getBean("petStore");

        assertNotNull(petStoreService.getAccountDao());
        assertNotNull(petStoreService.getItemDao());
        assertNotNull(petStoreService.getVersion());

        assertTrue(petStoreService.getAccountDao() instanceof AccountDao);
        assertTrue(petStoreService.getItemDao() instanceof ItemDao);
        assertEquals(1, petStoreService.getVersion());
    }

    @Test
    public void testGetBeanPropertyV4() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v4.xml");
        PetStoreService petStoreService = (PetStoreService) ctx.getBean("petStore");

        Assert.assertNotNull(petStoreService.getAccountDao());
        Assert.assertNotNull(petStoreService.getItemDao());
    }

    @Test
    public void testPlaceOrderV5() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v5.xml");
        PetStoreService petStoreService = (PetStoreService) ctx.getBean("petStore");

        Assert.assertNotNull(petStoreService.getAccountDao());
        Assert.assertNotNull(petStoreService. getItemDao());

        petStoreService.placeOrder();

        List<String> msgs = MessageTracker.getMsgs();

        Assert.assertEquals(3, msgs.size());
        Assert.assertEquals("start tx", msgs.get(0));
        Assert.assertEquals("place order", msgs.get(1));
        Assert.assertEquals("commit tx", msgs.get(2));
    }

}
