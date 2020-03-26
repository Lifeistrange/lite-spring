package com.lifeiscoding.spring.context;

import com.lifeiscoding.spring.core.Resource;
import com.lifeiscoding.spring.core.support.PackageResourceLoader;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class PackageResourceLoaderTest {

    @Test
    public void testGetResouces() throws IOException {
        PackageResourceLoader loader = new PackageResourceLoader();
        Resource[] resources = loader.getResources("com.lifeiscoding.spring.test");
        Assert.assertEquals(3, resources.length);
    }
}
