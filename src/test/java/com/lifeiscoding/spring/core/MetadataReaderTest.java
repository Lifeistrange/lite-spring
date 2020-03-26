package com.lifeiscoding.spring.core;

import com.lifeiscoding.spring.core.annotation.AnnotationAttributes;
import com.lifeiscoding.spring.core.type.AnnotationMetadata;
import com.lifeiscoding.spring.core.type.classreading.MetadataReader;
import com.lifeiscoding.spring.core.type.classreading.SimpleMetadataReader;
import com.lifeiscoding.spring.stereotype.Component;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class MetadataReaderTest {

    @Test
    public void testGetMetadata() throws IOException {
        ClassPathResource resource = new ClassPathResource("com/lifeiscoding/spring/test/service/PetStoreService.class");
        MetadataReader reader = new SimpleMetadataReader(resource);
        AnnotationMetadata amd = reader.getAnnotationMetadata();

        String annotation = Component.class.getName();

        Assert.assertTrue(amd.hasAnnotation(annotation));
        AnnotationAttributes attributes = amd.getAnnotationAttributes(annotation);
        Assert.assertEquals("petStore", attributes.get("value"));

        Assert.assertFalse(amd.isAbstract());
        Assert.assertFalse(amd.isFinal());
        Assert.assertEquals("com.lifeiscoding.spring.test.service.PetStoreService", amd.getClassName());

    }
}
