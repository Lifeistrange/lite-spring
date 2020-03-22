package com.lifeiscoding.spring.beans;

import java.util.List;

public interface BeanDefinition {
    boolean isSingleton();

    boolean isPrototype();

    String getId();

    SCOPE getScope();

    void setScope(String scope);

    void setScope(SCOPE scope);

    String getBeanClassName();

    List<PropertyValue> getPropertyValues();

    ConstructorArgument getConstructorArgument();

    boolean hasConstructorArgumentValues();

    enum SCOPE {SINGLETON, PROTOTYPE, DEFAULT}


}
