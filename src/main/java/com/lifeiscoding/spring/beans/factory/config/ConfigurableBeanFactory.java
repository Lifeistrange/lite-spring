package com.lifeiscoding.spring.beans.factory.config;

import com.lifeiscoding.spring.beans.factory.BeanFactory;

import java.util.List;

public interface ConfigurableBeanFactory extends AutowireCapableBeanFactory {
    ClassLoader getBeanClassLoader();

    void setBeanClassLoader(ClassLoader beanClassLoader);

    void addBeanPostProcessor(BeanPostProcessor postProcessor);
    List<BeanPostProcessor> getBeanPostProcessors();
}
