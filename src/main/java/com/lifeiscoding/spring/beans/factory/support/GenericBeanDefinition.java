package com.lifeiscoding.spring.beans.factory.support;

import com.lifeiscoding.spring.beans.BeanDefinition;
import com.lifeiscoding.spring.beans.ConstructorArgument;
import com.lifeiscoding.spring.beans.PropertyValue;

import java.util.ArrayList;
import java.util.List;

public class GenericBeanDefinition implements BeanDefinition {

    private String id;
    private String beanClassName;
    private List<PropertyValue> propertyValues = new ArrayList<>();
    private ConstructorArgument constructorArgument = new ConstructorArgument();
    private boolean singleton = true;
    private boolean prototype = false;
    private SCOPE scope = SCOPE.DEFAULT;

    public GenericBeanDefinition() {};

    public GenericBeanDefinition(String id, String beanClassName) {
        this.id = id;
        this.beanClassName = beanClassName;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
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
    public SCOPE getScope() {
        return scope;
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


    public void setBeanClassName(String className) {
        this.beanClassName = className;
    }

    public String getBeanClassName() {
        return this.beanClassName;
    }

    @Override
    public List<PropertyValue> getPropertyValues() {
        return propertyValues;
    }

    @Override
    public ConstructorArgument getConstructorArgument() {
        return constructorArgument;
    }

    @Override
    public boolean hasConstructorArgumentValues() {
        return !constructorArgument.isEmpty();
    }
}
