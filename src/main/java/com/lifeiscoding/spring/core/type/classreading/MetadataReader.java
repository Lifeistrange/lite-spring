package com.lifeiscoding.spring.core.type.classreading;

import com.lifeiscoding.spring.core.Resource;
import com.lifeiscoding.spring.core.type.AnnotationMetadata;
import com.lifeiscoding.spring.core.type.ClassMetadata;

public interface MetadataReader {

    Resource getResource();

    ClassMetadata getClassMetadata();

    AnnotationMetadata getAnnotationMetadata();
}
