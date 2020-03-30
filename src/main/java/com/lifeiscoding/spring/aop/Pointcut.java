package com.lifeiscoding.spring.aop;

public interface Pointcut {

    MethodMatcher getMethodMatcher();
    String getExpression();
}
