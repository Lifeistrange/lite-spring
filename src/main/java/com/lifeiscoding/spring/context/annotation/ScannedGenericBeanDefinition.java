package com.lifeiscoding.spring.context.annotation;

import com.lifeiscoding.spring.beans.factory.annotation.AnnotatedBeanDefinition;
import com.lifeiscoding.spring.beans.factory.support.GenericBeanDefinition;
import com.lifeiscoding.spring.core.type.AnnotationMetadata;

public class ScannedGenericBeanDefinition extends GenericBeanDefinition implements AnnotatedBeanDefinition {
    private final AnnotationMetadata metadata;

    public ScannedGenericBeanDefinition(AnnotationMetadata annotationMetadata) {
        super();
        this.metadata = annotationMetadata;
        setBeanClassName(this.metadata.getClassName());
    }

    public final AnnotationMetadata getMetadata() {
        return this.metadata;
    }
}
