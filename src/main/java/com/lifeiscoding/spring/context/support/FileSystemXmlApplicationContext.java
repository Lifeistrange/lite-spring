package com.lifeiscoding.spring.context.support;


import com.lifeiscoding.spring.core.io.FileSystemResource;
import com.lifeiscoding.spring.core.io.Resource;

public class FileSystemXmlApplicationContext extends AbstractApplicationContext {

    public FileSystemXmlApplicationContext(String configFile) {
        super(configFile);
    }

    @Override
    protected Resource getResource(String configFile) {
        return new FileSystemResource(configFile);
    }
}
