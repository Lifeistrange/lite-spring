package com.lifeiscoding.spring;

import com.lifeiscoding.spring.beans.BeanDefinitionTest;
import com.lifeiscoding.spring.beans.BeanDefinitionValueResolverTest;
import com.lifeiscoding.spring.beans.BeanFactoryTest;
import com.lifeiscoding.spring.beans.BeansAllTest;
import com.lifeiscoding.spring.context.ApplicationContextTest;
import com.lifeiscoding.spring.context.ContextAllTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({BeansAllTest.class, ContextAllTest.class})
public class AllTest {
}
