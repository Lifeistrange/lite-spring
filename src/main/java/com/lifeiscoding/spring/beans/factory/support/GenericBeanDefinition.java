package com.lifeiscoding.spring.beans.factory.support;

import com.lifeiscoding.spring.beans.BeanDefinition;

public class GenericBeanDefinition implements BeanDefinition {

    private String id;
    private String beanClassName;

    public GenericBeanDefinition(String id, String beanClassName) {
        this.id = id;
        this.beanClassName = beanClassName;
    }

    public String getId() {
        return this.id;
    }

    public String getBeanClassName() {
        return this.beanClassName;
    }
}
