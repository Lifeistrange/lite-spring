package com.lifeiscoding.spring.beans.factory.support;

import com.lifeiscoding.spring.beans.BeanDefinition;

public interface BeanDefinitionRegistry {
    BeanDefinition getBeanDefinition(String petstore);

    void registerBeanDefinition(String id, BeanDefinition bd);
}
