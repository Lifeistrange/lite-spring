package com.lifeiscoding.spring;

import com.lifeiscoding.spring.aop.config.AspectInstanceFactory;
import com.lifeiscoding.spring.beans.factory.BeanFactory;
import com.lifeiscoding.spring.beans.factory.support.DefaultBeanFactory;
import com.lifeiscoding.spring.beans.factory.xml.XMLBeanDefinitionReader;
import com.lifeiscoding.spring.core.ClassPathResource;
import com.lifeiscoding.spring.core.Resource;
import com.lifeiscoding.spring.test.tx.TransactionManager;

import java.lang.reflect.Method;

public abstract class AbstractTest {

    public BeanFactory getBeanFactory(String configFile) {
        DefaultBeanFactory defaultBeanFactory = new DefaultBeanFactory();
        XMLBeanDefinitionReader reader = new XMLBeanDefinitionReader(defaultBeanFactory);
        Resource resource = new ClassPathResource(configFile);
        reader.loadBeanDefinition(resource);
        return defaultBeanFactory;
    }

    public Method getAdviceMethod(String methodName) throws Exception {
        return TransactionManager.class.getMethod(methodName);
    }

    public AspectInstanceFactory getAspectInstanceFactory(String targetBeanName) {
        AspectInstanceFactory factory = new AspectInstanceFactory();
        factory.setAspectBeanName(targetBeanName);
        return factory;
    }
}
