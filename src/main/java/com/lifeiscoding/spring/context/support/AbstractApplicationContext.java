package com.lifeiscoding.spring.context.support;

import com.lifeiscoding.spring.beans.factory.support.DefaultBeanFactory;
import com.lifeiscoding.spring.beans.factory.xml.XMLBeanDefinitionReader;
import com.lifeiscoding.spring.context.ApplicationContext;
import com.lifeiscoding.spring.core.io.Resource;
import com.lifeiscoding.spring.util.ClassUtils;

public abstract class AbstractApplicationContext implements ApplicationContext {

    private DefaultBeanFactory factory;
    private ClassLoader beanClassLoader;

    public AbstractApplicationContext(String configFile) {
        factory = new DefaultBeanFactory();
        factory.setBeanClassLoader(this.getBeanClassLoader());
        XMLBeanDefinitionReader reader = new XMLBeanDefinitionReader(factory);
        Resource resource = getResource(configFile);
        reader.loadBeanDefinition(resource);
    }

    @Override
    public Object getBean(String beanId) {
        return factory.getBean(beanId);
    }

    @Override
    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader = beanClassLoader;
        factory.setBeanClassLoader(this.getBeanClassLoader());
    }

    @Override
    public ClassLoader getBeanClassLoader() {
        return (this.beanClassLoader != null ? beanClassLoader : ClassUtils.getDefaultClassLoader());
    }

    protected abstract Resource getResource(String configFile);
}
