package com.lifeiscoding.spring.beans;

import com.lifeiscoding.spring.beans.factory.annotation.AutowiredFieldElement;
import com.lifeiscoding.spring.beans.factory.annotation.InjectionElement;
import com.lifeiscoding.spring.beans.factory.annotation.InjectionMetadata;
import com.lifeiscoding.spring.beans.factory.support.DefaultBeanFactory;
import com.lifeiscoding.spring.beans.factory.xml.XMLBeanDefinitionReader;
import com.lifeiscoding.spring.core.ClassPathResource;
import com.lifeiscoding.spring.core.Resource;
import com.lifeiscoding.spring.test.dao.AccountDao;
import com.lifeiscoding.spring.test.dao.ItemDao;
import com.lifeiscoding.spring.test.service.PetStoreService;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.LinkedList;

public class InjectionMetadataTest {

    @Test
    public void testInjection() throws Exception {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XMLBeanDefinitionReader reader = new XMLBeanDefinitionReader(factory);
        Resource resource = new ClassPathResource("petstore-v4.xml");
        reader.loadBeanDefinition(resource);

        Class<?> clz = PetStoreService.class;
        LinkedList<InjectionElement> elements = new LinkedList<>();

        {
            Field f = PetStoreService.class.getDeclaredField("accountDao");
            InjectionElement injectionElement = new AutowiredFieldElement(f, true, factory);
            elements.add(injectionElement);
        }

        {
            Field f = PetStoreService.class.getDeclaredField("itemDao");
            InjectionElement injectionElement = new AutowiredFieldElement(f, true, factory);
            elements.add(injectionElement);
        }

        InjectionMetadata metadata = new InjectionMetadata(clz, elements);
        PetStoreService petStoreService = new PetStoreService();
        metadata.inject(petStoreService);
        Assert.assertTrue(petStoreService.getAccountDao() instanceof AccountDao);
        Assert.assertTrue(petStoreService.getItemDao() instanceof ItemDao);
    }
}
