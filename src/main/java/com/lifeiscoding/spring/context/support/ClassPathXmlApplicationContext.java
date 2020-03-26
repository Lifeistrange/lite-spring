package com.lifeiscoding.spring.context.support;


import com.lifeiscoding.spring.core.ClassPathResource;
import com.lifeiscoding.spring.core.Resource;

public class ClassPathXmlApplicationContext extends AbstractApplicationContext {
    public ClassPathXmlApplicationContext(String configFile) {
        super(configFile);
    }

    @Override
    protected Resource getResource(String configFile) {
        return new ClassPathResource(configFile);
    }
}
