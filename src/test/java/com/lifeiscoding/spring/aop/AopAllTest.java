package com.lifeiscoding.spring.aop;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({PointcutTest.class,
        CglibAopProxyTest.class,
        MethodLocatingFactoryTest.class,
        ReflectiveMethodInvocationTest.class})
public class AopAllTest {
}
