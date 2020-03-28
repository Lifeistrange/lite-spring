package com.lifeiscoding.spring.beans.factory.config;

import com.lifeiscoding.spring.beans.factory.BeanFactory;

public interface AutowireCapableBeanFactory extends BeanFactory  {
    public Object resolveDependency(DependencyDescriptor descriptor);
}
