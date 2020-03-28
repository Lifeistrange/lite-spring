package com.lifeiscoding.spring.beans;

import com.lifeiscoding.spring.beans.factory.config.DependencyDescriptor;
import com.lifeiscoding.spring.beans.factory.support.DefaultBeanFactory;
import com.lifeiscoding.spring.beans.factory.xml.XMLBeanDefinitionReader;
import com.lifeiscoding.spring.core.ClassPathResource;
import com.lifeiscoding.spring.core.Resource;
import com.lifeiscoding.spring.test.dao.AccountDao;
import com.lifeiscoding.spring.test.service.PetStoreService;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;

public class DependencyDescriptorTest {
    @Test
    public void testResolveDependency() throws Exception {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XMLBeanDefinitionReader reader = new XMLBeanDefinitionReader(factory);
        Resource resource = new ClassPathResource("petstore-v4.xml");
        reader.loadBeanDefinition(resource);

        Field f = PetStoreService.class.getDeclaredField("accountDao");
        DependencyDescriptor descriptor = new DependencyDescriptor(f, true);
        Object o = factory.resolveDependency(descriptor);
        Assert.assertTrue(o instanceof AccountDao);
    }
}
