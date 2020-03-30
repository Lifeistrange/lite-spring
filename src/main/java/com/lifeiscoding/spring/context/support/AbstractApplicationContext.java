package com.lifeiscoding.spring.context.support;

import com.lifeiscoding.spring.beans.factory.NoSuchBeanDefinitionException;
import com.lifeiscoding.spring.beans.factory.annotation.AutowiredAnnotationProcessor;
import com.lifeiscoding.spring.beans.factory.config.ConfigurableBeanFactory;
import com.lifeiscoding.spring.beans.factory.support.DefaultBeanFactory;
import com.lifeiscoding.spring.beans.factory.xml.XMLBeanDefinitionReader;
import com.lifeiscoding.spring.context.ApplicationContext;
import com.lifeiscoding.spring.core.Resource;
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
        registerBeanPostProcessors(factory);
    }

    @Override
    public Object getBean(String beanId) {
        return factory.getBean(beanId);
    }

    public ClassLoader getBeanClassLoader() {
        return (this.beanClassLoader != null ? beanClassLoader : ClassUtils.getDefaultClassLoader());
    }

    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader = beanClassLoader;
        factory.setBeanClassLoader(this.getBeanClassLoader());
    }

    protected abstract Resource getResource(String configFile);

    protected void registerBeanPostProcessors(ConfigurableBeanFactory beanFactory) {
        AutowiredAnnotationProcessor postProcessor = new AutowiredAnnotationProcessor();
        postProcessor.setBeanFactory(beanFactory);
        beanFactory.addBeanPostProcessor(postProcessor);
    }

    @Override
    public Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        return this.factory.getType(name);
    }
}
