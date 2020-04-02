package com.lifeiscoding.spring.aop.aspectj;

import com.lifeiscoding.spring.aop.Advice;
import com.lifeiscoding.spring.aop.Pointcut;

import java.lang.reflect.Method;

public abstract class AbstractAspectJAdvice implements Advice {
    protected Method adviceMethod;
    protected Pointcut pc;
    protected Object adviceObject;

    public AbstractAspectJAdvice(Method adviceMethod, AspectJExpressionPointcut pointcut, Object adviceObject) {
        this.adviceMethod = adviceMethod;
        this.pc = pointcut;
        this.adviceObject = adviceObject;
    }

    @Override
    public Pointcut getPointcut() {
        return pc;
    }

    public void invokeAdviceMethod() throws  Throwable{
        adviceMethod.invoke(adviceObject);
    }
}
