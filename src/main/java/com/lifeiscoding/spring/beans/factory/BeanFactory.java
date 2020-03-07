package com.lifeiscoding.spring.beans.factory;


import com.lifeiscoding.spring.beans.BeanDefinition;

public interface BeanFactory {
    BeanDefinition getBeanDefinition(String petstore);

    Object getBean(String beanId);
}
