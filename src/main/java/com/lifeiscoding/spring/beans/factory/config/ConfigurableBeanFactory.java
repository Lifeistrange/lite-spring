package com.lifeiscoding.spring.beans.factory.config;

import com.lifeiscoding.spring.beans.factory.BeanFactory;

public interface ConfigurableBeanFactory extends BeanFactory {
    ClassLoader getBeanClassLoader();

    void setBeanClassLoader(ClassLoader beanClassLoader);
}
