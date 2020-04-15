package com.lifeiscoding.spring.beans.factory.support;

import com.lifeiscoding.spring.beans.BeanDefinition;
import com.lifeiscoding.spring.beans.BeansException;
import com.lifeiscoding.spring.beans.factory.BeanCreationException;
import com.lifeiscoding.spring.beans.factory.BeanFactory;
import com.lifeiscoding.spring.beans.factory.FactoryBean;
import com.lifeiscoding.spring.beans.factory.config.RuntimeBeanReference;
import com.lifeiscoding.spring.beans.factory.config.TypedStringValue;

public class BeanDefinitionValueResolver {

    private final AbstractBeanFactory factory;

    public BeanDefinitionValueResolver(AbstractBeanFactory factory) {
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
        } else if (value instanceof BeanDefinition) {
            BeanDefinition bd = (BeanDefinition) value;
            String innerBeanName = "(inner bean)" + bd.getBeanClassName() + "#" + Integer.toHexString(System.identityHashCode(bd));

            return resolveInnerBean(innerBeanName, bd);
        } else {
            return value;
        }

//        throw new RuntimeException("the value " + value + "has not implemented");
    }

    private Object resolveInnerBean(String innerBeanName, BeanDefinition bd) {
        try {
            Object innerBean = this.factory.createBean(bd);
            if (innerBean instanceof FactoryBean) {
                try {
                    return ((FactoryBean<?>) innerBean).getObject();
                } catch (Exception e) {
                    throw new BeanCreationException(innerBeanName, "FactoryBean threw exception on object creation", e);
                }
            } else {
                return innerBean;
            }
        } catch (BeansException ex) {
            throw new BeanCreationException(innerBeanName,
                    "Cannot create inner bean '" + innerBeanName + "' " + (bd != null && bd.getBeanClassName() != null ? "of type [" + bd.getBeanClassName() + "] " : ""),
                    ex);
        }
    }
}
