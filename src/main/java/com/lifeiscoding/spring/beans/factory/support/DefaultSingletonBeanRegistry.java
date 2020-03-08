package com.lifeiscoding.spring.beans.factory.support;

import com.lifeiscoding.spring.beans.factory.BeanCreationException;
import com.lifeiscoding.spring.beans.factory.BeanRegisterException;
import com.lifeiscoding.spring.beans.factory.config.SingletonBeanRegistry;
import com.lifeiscoding.spring.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(64);

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        Assert.notNull(beanName, "'beanName' must be not null");

        Object oldObject = this.singletonObjects.get(beanName);
        if (oldObject != null) {
            throw new BeanRegisterException("can't register object '" + singletonObject + "', there is already object '" + oldObject + "'");
        }
        this.singletonObjects.put(beanName, singletonObject);
    }

    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }
}
