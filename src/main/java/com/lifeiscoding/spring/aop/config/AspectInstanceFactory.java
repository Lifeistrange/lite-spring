package com.lifeiscoding.spring.aop.config;

import com.lifeiscoding.spring.beans.factory.BeanFactory;
import com.lifeiscoding.spring.beans.factory.BeanFactoryAware;
import com.lifeiscoding.spring.util.StringUtils;

public class AspectInstanceFactory implements BeanFactoryAware {
    private String aspectBeanName;

    private BeanFactory beanFactory;

    public void setAspectBeanName(String aspectBeanName) {
        this.aspectBeanName = aspectBeanName;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
        if (!StringUtils.hasText(this.aspectBeanName)) {
            throw new IllegalArgumentException("'aspectBeanName' is required");
        }
    }

    public Object getAspectInstance() throws Exception {
        return this.beanFactory.getBean(this.aspectBeanName);
    }
}
