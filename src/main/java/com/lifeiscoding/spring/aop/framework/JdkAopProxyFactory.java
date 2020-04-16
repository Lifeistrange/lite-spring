package com.lifeiscoding.spring.aop.framework;

import com.lifeiscoding.spring.aop.Advice;
import com.lifeiscoding.spring.util.Assert;
import com.lifeiscoding.spring.util.ClassUtils;
import org.aopalliance.intercept.MethodInterceptor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public class JdkAopProxyFactory implements AopProxyFactory, InvocationHandler {

    private static final Log logger = LogFactory.getLog(JdkAopProxyFactory.class);

    private final AopConfig config;

    public JdkAopProxyFactory(AopConfig config) throws AopConfigException {
        Assert.notNull(config, "AdvisedSupport must not be null");
        if (config.getAdvices().size() == 0) {
            throw new AopConfigException("No advices specified");
        }
        this.config = config;
    }

    @Override
    public Object getProxy() {
        return getProxy(ClassUtils.getDefaultClassLoader());
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        Class<?>[] proxiedInterfaces = config.getProxiedInterfaces();

        return Proxy.newProxyInstance(classLoader, proxiedInterfaces, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object target = this.config.getTargetObject();

        Object retVal;

        List<Advice> chain = this.config.getAdvices(method);

        if (chain.isEmpty()) {
            retVal = method.invoke(target, args);
        } else {
            List<MethodInterceptor> interceptors = new ArrayList<>();
            interceptors.addAll(chain);
            retVal = new ReflectiveMethodInvocation(target, method, args, interceptors).proceed();
        }
        return retVal;
    }
}
