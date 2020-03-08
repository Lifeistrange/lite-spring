package com.lifeiscoding.spring.beans;

public interface BeanDefinition {
     enum SCOPE {SINGLETON, PROTOTYPE, DEFAULT}

     boolean isSingleton();
     boolean isPrototype();

     void setScope(String scope);
     void setScope(SCOPE scope);
     SCOPE getScope();

     String getBeanClassName();

}
