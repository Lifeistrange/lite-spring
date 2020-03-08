package com.lifeiscoding.spring.beans.factory;

import com.lifeiscoding.spring.beans.BeansException;

public class BeanRegisterException extends BeansException {
    public BeanRegisterException(String msg) {
        super(msg);
    }

    public BeanRegisterException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
