package com.lifeiscoding.spring.aop;

import java.lang.reflect.Method;

public interface MethodMatcher {

    boolean matches(Method method);
}
