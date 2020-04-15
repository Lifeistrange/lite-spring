package com.lifeiscoding.spring.aop;

import com.lifeiscoding.spring.AbstractTest;
import com.lifeiscoding.spring.aop.aspectj.AspectJAfterReturningAdvice;
import com.lifeiscoding.spring.aop.aspectj.AspectJBeforeAdvice;
import com.lifeiscoding.spring.aop.aspectj.AspectJExpressionPointcut;
import com.lifeiscoding.spring.aop.config.AspectInstanceFactory;
import com.lifeiscoding.spring.aop.framework.AopConfig;
import com.lifeiscoding.spring.aop.framework.AopConfigSupport;
import com.lifeiscoding.spring.aop.framework.CglibProxyFactory;
import com.lifeiscoding.spring.beans.factory.BeanFactory;
import com.lifeiscoding.spring.test.service.PetStoreService;
import com.lifeiscoding.spring.test.util.MessageTracker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class CglibAopProxyTest extends AbstractTest {

    private static AspectJBeforeAdvice beforeAdvice = null;
    private static AspectJAfterReturningAdvice afterAdvice = null;
    private static AspectJExpressionPointcut pc = null;
    private BeanFactory beanFactory = null;
    private AspectInstanceFactory aspectInstanceFactory = null;

    @Before
    public void setUp() throws Exception {
        MessageTracker.clearMsgs();

        String expression = "execution(* com.lifeiscoding.spring.test.service.*.placeOrder(..))";
        pc = new AspectJExpressionPointcut();
        pc.setExpression(expression);

        beanFactory = this.getBeanFactory("petstore-v5.xml");
        aspectInstanceFactory = this.getAspectInstanceFactory("tx");
        aspectInstanceFactory.setBeanFactory(beanFactory);

        beforeAdvice = new AspectJBeforeAdvice(
                getAdviceMethod("start"),
                pc,
                aspectInstanceFactory
        );
        afterAdvice = new AspectJAfterReturningAdvice(
                getAdviceMethod("commit"),
                pc,
                aspectInstanceFactory
        );
    }

    @Test
    public void testGetProxy() {

        AopConfig config = new AopConfigSupport();

        config.addAdvice(beforeAdvice);
        config.addAdvice(afterAdvice);
        config.setTargetObject(new PetStoreService());


        CglibProxyFactory proxyFactory = new CglibProxyFactory(config);

        PetStoreService proxy = (PetStoreService)proxyFactory.getProxy();

        proxy.placeOrder();


        List<String> msgs = MessageTracker.getMsgs();
        Assert.assertEquals(3, msgs.size());
        Assert.assertEquals("start tx", msgs.get(0));
        Assert.assertEquals("place order", msgs.get(1));
        Assert.assertEquals("commit tx", msgs.get(2));

        proxy.toString();

    }
}
