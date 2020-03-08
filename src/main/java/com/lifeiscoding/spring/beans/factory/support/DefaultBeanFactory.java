package com.lifeiscoding.spring.beans.factory.support;

import com.lifeiscoding.spring.beans.BeanDefinition;
import com.lifeiscoding.spring.beans.factory.BeanCreationException;
import com.lifeiscoding.spring.beans.factory.config.ConfigurableBeanFactory;
import com.lifeiscoding.spring.util.ClassUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultBeanFactory extends DefaultSingletonBeanRegistry
        implements ConfigurableBeanFactory, BeanDefinitionRegistry {

    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();
    private ClassLoader beanClassLoader;

    public BeanDefinition getBeanDefinition(String beanID) {
        return beanDefinitionMap.get(beanID);
    }

    @Override
    public void registerBeanDefinition(String id, BeanDefinition bd) {
        this.beanDefinitionMap.put(id, bd);
    }

    public Object getBean(String beanID) {
        BeanDefinition bd = this.getBeanDefinition(beanID);
        if (bd == null) {
            throw new BeanCreationException("Bean Definition does not exist");
        }
        if (bd.isSingleton()) {
            Object bean = this.getSingleton(bd.getBeanClassName());
            if (bean == null) {
                bean = this.createBean(bd);
                this.registerSingleton(bd.getBeanClassName(), bean);
            }
            return bean;
        }
        return createBean(bd);
    }

    private Object createBean(BeanDefinition bd) {
        ClassLoader cl = ClassUtils.getDefaultClassLoader();
        String beanClassName = bd.getBeanClassName();
        try {
            Class<?> clz = cl.loadClass(beanClassName);
            return clz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new BeanCreationException(beanClassName, e);
        }
    }

    @Override
    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader = beanClassLoader;
    }

    @Override
    public ClassLoader getBeanClassLoader() {
        return (this.beanClassLoader != null ? beanClassLoader: ClassUtils.getDefaultClassLoader());
    }
}
