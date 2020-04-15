package com.lifeiscoding.spring.aop.aspectj;

import com.lifeiscoding.spring.aop.Advice;
import com.lifeiscoding.spring.aop.Pointcut;
import com.lifeiscoding.spring.aop.config.AspectInstanceFactory;

import java.lang.reflect.Method;

public abstract class AbstractAspectJAdvice implements Advice {
    protected Method adviceMethod;
    protected AspectJExpressionPointcut pc;
    protected AspectInstanceFactory adviceObjectFactory;

    public AbstractAspectJAdvice(Method adviceMethod, AspectJExpressionPointcut pointcut, AspectInstanceFactory adviceObjectFactory) {
        this.adviceMethod = adviceMethod;
        this.pc = pointcut;
        this.adviceObjectFactory = adviceObjectFactory;
    }

    @Override
    public Pointcut getPointcut() {
        return pc;
    }

    public void invokeAdviceMethod() throws  Throwable{
        adviceMethod.invoke(adviceObjectFactory.getAspectInstance());
    }

    public Method getAdviceMethod() {
        return adviceMethod;
    }

    public Object getAdviceInstance() throws Exception {
        return adviceObjectFactory.getAspectInstance();
    }
}
