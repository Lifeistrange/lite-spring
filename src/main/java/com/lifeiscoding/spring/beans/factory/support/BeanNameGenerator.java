package com.lifeiscoding.spring.beans.factory.support;

import com.lifeiscoding.spring.beans.BeanDefinition;

public interface BeanNameGenerator {

    String generateBeanName(BeanDefinition bd, BeanDefinitionRegistry registry);
}
