package com.lifeiscoding.spring.beans;

import java.util.List;

public interface BeanDefinition {
    boolean isSingleton();

    boolean isPrototype();

    boolean isSynthetic();

    String getId();

    SCOPE getScope();

    void setScope(String scope);

    void setScope(SCOPE scope);

    String getBeanClassName();

    List<PropertyValue> getPropertyValues();

    ConstructorArgument getConstructorArgument();

    boolean hasConstructorArgumentValues();

    Class<?> resolveBeanClass(ClassLoader classLoader) throws ClassNotFoundException;

    Class<?> getBeanClass() throws IllegalStateException;

    boolean hasBeanClass();

    enum SCOPE {SINGLETON, PROTOTYPE, DEFAULT}


}
