package com.lifeiscoding.spring.beans.factory;

import com.lifeiscoding.spring.beans.BeansException;

public interface BeanFactoryAware {

    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
