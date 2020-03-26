package com.lifeiscoding.spring.core;

import com.lifeiscoding.spring.context.ApplicationContextTest;
import com.lifeiscoding.spring.context.ClassPathBeanDefinitionScannerTest;
import com.lifeiscoding.spring.context.PackageResourceLoaderTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ClassReaderTest.class,
        MetadataReaderTest.class})
public class CoreAllTest {
}
