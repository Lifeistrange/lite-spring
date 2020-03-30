package com.lifeiscoding.spring;

import com.lifeiscoding.spring.aop.AopAllTest;
import com.lifeiscoding.spring.beans.BeansAllTest;
import com.lifeiscoding.spring.context.ContextAllTest;
import com.lifeiscoding.spring.core.CoreAllTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({BeansAllTest.class, ContextAllTest.class, CoreAllTest.class, AopAllTest.class})
public class AllTest {
}
