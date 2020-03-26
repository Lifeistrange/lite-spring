package com.lifeiscoding.spring.beans;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({BeanFactoryTest.class,
        BeanDefinitionTest.class,
        BeanDefinitionValueResolverTest.class,
        ConstructorResolverTest.class,
        CustomBooleanEditorTest.class,
        CustomNumberEditorTest.class,
        TypeConverterTest.class,
        XMLBeanDefinitionReaderTest.class})
public class BeansAllTest {
}
