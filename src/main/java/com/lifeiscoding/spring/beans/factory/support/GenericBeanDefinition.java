package com.lifeiscoding.spring.beans.factory.support;

import com.lifeiscoding.spring.beans.BeanDefinition;

public class GenericBeanDefinition implements BeanDefinition {

    private String id;
    private String beanClassName;
    private boolean singleton = true;
    private boolean prototype = false;
    private SCOPE scope = SCOPE.DEFAULT;

    public GenericBeanDefinition(String id, String beanClassName) {
        this.id = id;
        this.beanClassName = beanClassName;
    }

    public String getId() {
        return this.id;
    }

    @Override
    public boolean isSingleton() {
        return singleton;
    }

    @Override
    public boolean isPrototype() {
        return prototype;
    }

    @Override
    public void setScope(String scope) {
        setScope(SCOPE.valueOf(scope));
    }

    @Override
    public void setScope(SCOPE scope) {
        this.scope = scope;
        this.singleton = scope == SCOPE.SINGLETON || scope == SCOPE.DEFAULT;
        this.prototype = scope == SCOPE.PROTOTYPE;
    }

    @Override
    public SCOPE getScope() {
        return scope;
    }

    public String getBeanClassName() {
        return this.beanClassName;
    }
}
