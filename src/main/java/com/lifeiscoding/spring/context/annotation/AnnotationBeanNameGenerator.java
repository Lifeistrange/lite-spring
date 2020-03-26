package com.lifeiscoding.spring.context.annotation;

import com.lifeiscoding.spring.beans.BeanDefinition;
import com.lifeiscoding.spring.beans.factory.annotation.AnnotatedBeanDefinition;
import com.lifeiscoding.spring.beans.factory.support.BeanDefinitionRegistry;
import com.lifeiscoding.spring.beans.factory.support.BeanNameGenerator;
import com.lifeiscoding.spring.core.annotation.AnnotationAttributes;
import com.lifeiscoding.spring.core.type.AnnotationMetadata;
import com.lifeiscoding.spring.util.ClassUtils;
import com.lifeiscoding.spring.util.StringUtils;

import java.beans.Introspector;
import java.util.Set;

public class AnnotationBeanNameGenerator implements BeanNameGenerator {
    @Override
    public String generateBeanName(BeanDefinition bd, BeanDefinitionRegistry registry) {
        if (bd instanceof AnnotatedBeanDefinition) {
            String beanName = determineBeanNameFromAnnotation((AnnotatedBeanDefinition) bd);
            if (StringUtils.hasText(beanName)) {
                return beanName;
            }
        }
        return buildDefaultBeanName(bd, registry);
    }

    protected String determineBeanNameFromAnnotation(AnnotatedBeanDefinition bd) {
        AnnotationMetadata amd = bd.getMetadata();
        Set<String> types = amd.getAnnotationTypes();
        String beanName = null;
        for (String type: types) {
            AnnotationAttributes attributes = amd.getAnnotationAttributes(type);
            if (attributes.get("value") != null) {
                Object value = attributes.get("value");
                if (value instanceof String) {
                    String strVal = (String) value;
                    if (StringUtils.hasLength(strVal)) {
                        beanName = strVal;
                    }
                }
            }
        }
        return beanName;
    }

    protected String buildDefaultBeanName(BeanDefinition bd, BeanDefinitionRegistry registry) {
        return buildDefaultBeanName(bd);
    }

    protected String buildDefaultBeanName(BeanDefinition bd) {
        String shortClassName = ClassUtils.getShortName(bd.getBeanClassName());
        return Introspector.decapitalize(shortClassName);
    }
}
