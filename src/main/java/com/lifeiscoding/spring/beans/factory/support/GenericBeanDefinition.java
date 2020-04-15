package com.lifeiscoding.spring.beans.factory.support;

import com.lifeiscoding.spring.aop.config.MethodLocatingFactory;
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
    private boolean synthetic = false;
    private SCOPE scope = SCOPE.DEFAULT;
    private Class<?> beanClass;

    public GenericBeanDefinition() {
    }

    public GenericBeanDefinition(String id, String beanClassName) {
        this.id = id;
        this.beanClassName = beanClassName;
    }

    public GenericBeanDefinition(Class<?> clz) {
        this.beanClass = clz;
        this.beanClassName = clz.getName();
    }

    @Override
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
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
    public boolean isSynthetic() {
        return synthetic;
    }

    public void setSynthetic(boolean synthetic) {
        this.synthetic = synthetic;
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

    public String getBeanClassName() {
        return this.beanClassName;
    }

    public void setBeanClassName(String className) {
        this.beanClassName = className;
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

    @Override
    public Class<?> resolveBeanClass(ClassLoader classLoader) throws ClassNotFoundException {
        String className = getBeanClassName();
        if (className == null) {
            return null;
        }
        Class<?> resolvedClass = classLoader.loadClass(className);
        this.beanClass = resolvedClass;
        return resolvedClass;
    }

    @Override
    public Class<?> getBeanClass() throws IllegalStateException {
        if (this.beanClass == null) {
            throw new IllegalStateException("Bean class name [" + this.getBeanClassName() + "]has not benn resolved into an actual Class");
        }
        return this.beanClass;
    }

    @Override
    public boolean hasBeanClass() {
        return this.beanClass != null;
    }
}
