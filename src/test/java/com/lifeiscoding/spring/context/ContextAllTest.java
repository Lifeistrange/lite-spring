package com.lifeiscoding.spring.context;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ApplicationContextTest.class,
        ClassPathBeanDefinitionScannerTest.class,
        PackageResourceLoaderTest.class})
public class ContextAllTest {
}
