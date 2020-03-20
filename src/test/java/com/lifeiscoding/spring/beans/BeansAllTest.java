package com.lifeiscoding.spring.beans;

import com.lifeiscoding.spring.context.ContextAllTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({BeanFactoryTest.class,
        BeanDefinitionTest.class,
        BeanDefinitionValueResolverTest.class,
        CustomBooleanEditorTest.class,
        CustomNumberEditorTest.class,
        TypeConverterTest.class})
public class BeansAllTest {
}
