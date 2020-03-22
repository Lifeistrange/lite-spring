package com.lifeiscoding.spring.beans;

import com.lifeiscoding.spring.beans.factory.support.ConstructorResolver;
import com.lifeiscoding.spring.beans.factory.support.DefaultBeanFactory;
import com.lifeiscoding.spring.beans.factory.xml.XMLBeanDefinitionReader;
import com.lifeiscoding.spring.core.io.ClassPathResource;
import com.lifeiscoding.spring.core.io.Resource;
import com.lifeiscoding.spring.test.service.PetStoreService;
import org.junit.Assert;
import org.junit.Test;

public class ConstructorResolverTest {

    @Test
    public void  testAutowireConstrcutor() {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XMLBeanDefinitionReader reader = new XMLBeanDefinitionReader(factory);
        Resource resource = new ClassPathResource("petstore-v3.xml");
        reader.loadBeanDefinition(resource);

        BeanDefinition bd = factory.getBeanDefinition("petStore");
        ConstructorResolver resolver = new ConstructorResolver(factory);

        PetStoreService service = (PetStoreService) resolver.autowireConstructor(bd);

        Assert.assertEquals(1, service.getVersion());
        Assert.assertNotNull(service.getAccountDao());
        Assert.assertNotNull(service.getItemDao());
    }
}
