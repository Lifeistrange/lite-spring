package com.lifeiscoding.spring.aop.framework;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.List;

public class ReflectiveMethodInvocation implements MethodInvocation {

    protected final Object targetObject;
    protected final Method targetMethod;
    protected final List<MethodInterceptor> interceptors;
    protected Object[] arguments;

    private int currentInterceptorIndex = -1;

    public ReflectiveMethodInvocation(Object targetObject, Method targetMethod, Object[] arguments, List<MethodInterceptor> interceptors) {
        this.targetObject = targetObject;
        this.targetMethod = targetMethod;
        this.arguments = arguments;
        this.interceptors = interceptors;
    }


    @Override
    public Method getMethod() {
        return targetMethod;
    }

    @Override
    public Object[] getArguments() {
        return (this.arguments != null ? this.arguments : new Object[0]);
    }

    @Override
    public Object proceed() throws Throwable {
        if (this.currentInterceptorIndex == this.interceptors.size() -1) {
            return invokeJoinpoint();
        }
        this.currentInterceptorIndex++;

        MethodInterceptor interceptor = this.interceptors.get(this.currentInterceptorIndex);
        return interceptor.invoke(this);
    }

    private Object invokeJoinpoint() throws Throwable {
        return this.targetMethod.invoke(this.targetObject, this.arguments);
    }

    @Override
    public Object getThis() {
        return this.targetObject;
    }

    @Override
    public AccessibleObject getStaticPart() {
        return this.targetMethod;
    }
}
