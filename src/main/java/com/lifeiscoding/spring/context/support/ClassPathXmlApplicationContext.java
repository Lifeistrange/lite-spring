package com.lifeiscoding.spring.context.support;


import com.lifeiscoding.spring.core.io.ClassPathResource;
import com.lifeiscoding.spring.core.io.Resource;

public class ClassPathXmlApplicationContext extends AbstractApplicationContext {
    public ClassPathXmlApplicationContext(String configFile) {
        super(configFile);
    }

    @Override
    protected Resource getResource(String configFile) {
        return new ClassPathResource(configFile);
    }
}
