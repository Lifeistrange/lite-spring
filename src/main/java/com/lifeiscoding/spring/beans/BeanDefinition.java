package com.lifeiscoding.spring.beans;

import java.util.List;

public interface BeanDefinition {
    boolean isSingleton();

    boolean isPrototype();

    SCOPE getScope();

    void setScope(String scope);

    void setScope(SCOPE scope);

    String getBeanClassName();

    List<PropertyValue> getPropertyValues();

    ConstructorArgument getConstructorArgument();

    enum SCOPE {SINGLETON, PROTOTYPE, DEFAULT}


}
