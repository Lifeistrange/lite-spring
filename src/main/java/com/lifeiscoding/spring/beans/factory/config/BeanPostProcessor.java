package com.lifeiscoding.spring.beans.factory.config;

import com.lifeiscoding.spring.beans.BeansException;

public interface BeanPostProcessor {

    Object beforeInitialization(Object bean, String beanName) throws BeansException;

    Object afterInitialization(Object bean, String beanName) throws BeansException;
}
