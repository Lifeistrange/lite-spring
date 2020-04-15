package com.lifeiscoding.spring.aop;

import com.lifeiscoding.spring.AbstractTest;
import com.lifeiscoding.spring.aop.aspectj.AspectJAfterReturningAdvice;
import com.lifeiscoding.spring.aop.aspectj.AspectJAfterThrowingAdvice;
import com.lifeiscoding.spring.aop.aspectj.AspectJBeforeAdvice;
import com.lifeiscoding.spring.aop.config.AspectInstanceFactory;
import com.lifeiscoding.spring.aop.framework.ReflectiveMethodInvocation;
import com.lifeiscoding.spring.beans.factory.BeanFactory;
import com.lifeiscoding.spring.test.service.PetStoreService;
import com.lifeiscoding.spring.test.util.MessageTracker;
import org.aopalliance.intercept.MethodInterceptor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ReflectiveMethodInvocationTest extends AbstractTest {

    private AspectJBeforeAdvice beforeAdvice = null;
    private AspectJAfterReturningAdvice afterAdvice = null;
    private AspectJAfterThrowingAdvice afterThrowingAdvice = null;
    private PetStoreService petStoreService = null;
    private BeanFactory beanFactory = null;
    private AspectInstanceFactory aspectInstanceFactory = null;

    @Before
    public void setup() throws Exception {
        MessageTracker.clearMsgs();

        petStoreService = new PetStoreService();
        beanFactory = this.getBeanFactory("petstore-v5.xml");
        aspectInstanceFactory = this.getAspectInstanceFactory("tx");
        aspectInstanceFactory.setBeanFactory(beanFactory);

        beforeAdvice = new AspectJBeforeAdvice(
                getAdviceMethod("start"),
                null,
                aspectInstanceFactory
        );
        afterAdvice = new AspectJAfterReturningAdvice(
                getAdviceMethod("commit"),
                null,
                aspectInstanceFactory
        );
        afterThrowingAdvice = new AspectJAfterThrowingAdvice(
                getAdviceMethod("rollback"),
                null,
                aspectInstanceFactory
        );
    }

    @Test
    public void testMethodInvocation() throws Throwable {
        Method targetMethod = PetStoreService.class.getMethod("placeOrder");

        List<MethodInterceptor> interceptors = new ArrayList<>();
        interceptors.add(beforeAdvice);
        interceptors.add(afterAdvice);

        ReflectiveMethodInvocation mi = new ReflectiveMethodInvocation(petStoreService, targetMethod, new Object[0], interceptors);

        mi.proceed();

        List<String> msgs = MessageTracker.getMsgs();
        Assert.assertEquals(3, msgs.size());
        Assert.assertEquals("start tx", msgs.get(0));
        Assert.assertEquals("place order", msgs.get(1));
        Assert.assertEquals("commit tx", msgs.get(2));
    }

    @Test
    public void testAfterThrowing() throws  Throwable {
        Method targetMethod = PetStoreService.class.getMethod("placeOrderWithException");
        List<MethodInterceptor> methodInterceptors = new ArrayList<>();
        methodInterceptors.add(afterThrowingAdvice);
        methodInterceptors.add(beforeAdvice);

        ReflectiveMethodInvocation mi = new ReflectiveMethodInvocation(petStoreService, targetMethod, new Object[0], methodInterceptors);

        try {
            mi.proceed();
        } catch (Throwable throwable) {
            List<String> msgs = MessageTracker.getMsgs();
            Assert.assertEquals(2, msgs.size());
            Assert.assertEquals("start tx", msgs.get(0));
            Assert.assertEquals("rollback tx", msgs.get(1));
            return;
        }

        Assert.fail("No Exception thrown");
    }
}
