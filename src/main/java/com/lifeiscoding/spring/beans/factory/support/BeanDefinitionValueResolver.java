package com.lifeiscoding.spring.beans.factory.support;

import com.lifeiscoding.spring.beans.factory.config.RuntimeBeanReference;
import com.lifeiscoding.spring.beans.factory.config.TypedStringValue;

public class BeanDefinitionValueResolver {

    private final DefaultBeanFactory factory;

    public BeanDefinitionValueResolver(DefaultBeanFactory factory) {
        this.factory = factory;
    }

    public Object resolveValueIfNecessary(Object value) {
        if (value instanceof RuntimeBeanReference) {
            RuntimeBeanReference ref = (RuntimeBeanReference) value;
            String name = ref.getBeanName();
            Object bean = factory.getBean(name);
            return bean;
        } else if (value instanceof TypedStringValue) {
            return ((TypedStringValue) value).getAttributeValue();
        }

        throw new RuntimeException("the value " + value + "has not implemented");
    }
}
