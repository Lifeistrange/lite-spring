package com.lifeiscoding.spring.beans.factory.annotation;

import com.lifeiscoding.spring.beans.BeanDefinition;
import com.lifeiscoding.spring.core.type.AnnotationMetadata;

public interface AnnotatedBeanDefinition extends BeanDefinition {
    AnnotationMetadata getMetadata();
}
