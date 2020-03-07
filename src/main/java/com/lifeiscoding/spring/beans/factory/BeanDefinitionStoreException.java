package com.lifeiscoding.spring.beans.factory;

import com.lifeiscoding.spring.beans.BeansException;

public class BeanDefinitionStoreException extends BeansException {

    public BeanDefinitionStoreException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
