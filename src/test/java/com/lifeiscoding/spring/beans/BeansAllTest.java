package com.lifeiscoding.spring.beans;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({AutowiredAnnotationProcessorTest.class,
        BeanDefinitionTest.class,
        BeanDefinitionValueResolverTest.class,
        BeanFactoryTest.class,
        ConstructorResolverTest.class,
        CustomBooleanEditorTest.class,
        CustomNumberEditorTest.class,
        DependencyDescriptorTest.class,
        InjectionMetadataTest.class,
        TypeConverterTest.class,
        XMLBeanDefinitionReaderTest.class,})
public class BeansAllTest {
}
