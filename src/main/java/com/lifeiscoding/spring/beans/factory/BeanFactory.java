package com.lifeiscoding.spring.beans.factory;

import com.lifeiscoding.spring.aop.Advice;
import com.lifeiscoding.spring.beans.BeanDefinition;

import java.util.List;

public interface BeanFactory {
    Object getBean(String beanId);

    Class<?> getType(String name) throws NoSuchBeanDefinitionException;

    List<Object> getBeansByType(Class<?> type);

}
