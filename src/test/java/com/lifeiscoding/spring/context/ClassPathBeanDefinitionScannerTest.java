package com.lifeiscoding.spring.context;

import com.lifeiscoding.spring.beans.BeanDefinition;
import com.lifeiscoding.spring.beans.factory.support.DefaultBeanFactory;
import com.lifeiscoding.spring.context.annotation.ClassPathBeanDefinitionScanner;
import com.lifeiscoding.spring.context.annotation.ScannedGenericBeanDefinition;
import com.lifeiscoding.spring.core.annotation.AnnotationAttributes;
import com.lifeiscoding.spring.core.type.AnnotationMetadata;
import com.lifeiscoding.spring.stereotype.Component;
import org.junit.Assert;
import org.junit.Test;

public class ClassPathBeanDefinitionScannerTest {

    @Test
    public void testParseScanedBean() {
        DefaultBeanFactory factory = new DefaultBeanFactory();

        String basePackages = "com.lifeiscoding.spring.test.service,com.lifeiscoding.spring.test.dao";

        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(factory);
        scanner.doScan(basePackages);

        String annotation = Component.class.getName();

        {
            BeanDefinition bd = factory.getBeanDefinition("petStore");
            Assert.assertTrue(bd instanceof ScannedGenericBeanDefinition);
            ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition) bd;
            AnnotationMetadata amd = sbd.getMetadata();

            Assert.assertTrue(amd.hasAnnotation(annotation));
            AnnotationAttributes attributes = amd.getAnnotationAttributes(annotation);
            Assert.assertEquals("petStore", attributes.get("value"));
        }
        {
            BeanDefinition bd = factory.getBeanDefinition("accountDao");
            Assert.assertTrue(bd instanceof ScannedGenericBeanDefinition);
            ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition) bd;
            AnnotationMetadata amd = sbd.getMetadata();
            Assert.assertTrue(amd.hasAnnotation(annotation));
        }
        {
            BeanDefinition bd = factory.getBeanDefinition("itemDao");
            Assert.assertTrue(bd instanceof ScannedGenericBeanDefinition);
            ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition) bd;
            AnnotationMetadata amd = sbd.getMetadata();
            Assert.assertTrue(amd.hasAnnotation(annotation));
        }

    }
}
