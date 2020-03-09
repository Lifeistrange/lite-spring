package com.lifeiscoding.spring;

import com.lifeiscoding.spring.beans.BeanFactoryTest;
import com.lifeiscoding.spring.context.ApplicationContextTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ApplicationContextTest.class, BeanFactoryTest.class})
public class AllTest {
}
