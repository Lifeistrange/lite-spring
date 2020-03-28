package com.lifeiscoding.spring.beans.factory.annotation;

import com.lifeiscoding.spring.beans.factory.BeanCreationException;
import com.lifeiscoding.spring.beans.factory.config.DependencyDescriptor;
import com.lifeiscoding.spring.beans.factory.support.DefaultBeanFactory;
import com.lifeiscoding.spring.util.ReflectionUtils;

import java.lang.reflect.Field;

public class AutowiredFieldElement extends InjectionElement {

    boolean required;

    public AutowiredFieldElement(Field f, boolean required, DefaultBeanFactory factory) {
        super(f, factory);
        this.required = required;
    }

    @Override
    public void inject(Object target) {
        Field field = this.getField();
        try {
            DependencyDescriptor desc = new DependencyDescriptor(field, this.required);

            Object value = factory.resolveDependency(desc);

            if (value != null) {
                ReflectionUtils.makeAccessible(field);
                field.set(target, value);
            }
        } catch (Throwable ex) {
            throw new BeanCreationException("Could not autowire field: " + field, ex);
        }
    }

    private Field getField() {
        return (Field) this.member;
    }
}
