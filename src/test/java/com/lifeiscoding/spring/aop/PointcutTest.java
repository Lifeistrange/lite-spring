package com.lifeiscoding.spring.aop;

import com.lifeiscoding.spring.aop.aspectj.AspectJExpressionPointcut;
import com.lifeiscoding.spring.test.service.PetStoreService;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;

public class PointcutTest {

    @Test
    public void testPointcut() throws Exception {
        String expression = "execution(* com.lifeiscoding.spring.test.service.*.placeOrder(..))";
        AspectJExpressionPointcut pc = new AspectJExpressionPointcut();
        pc.setExpression(expression);

        MethodMatcher mm = pc.getMethodMatcher();

        {
            Class<?> targetClass = PetStoreService.class;

            Method method1 = targetClass.getMethod("placeOrder");
            Assert.assertTrue(mm.matches(method1));

            Method method2 = targetClass.getMethod("getAccountDao");
            Assert.assertFalse(mm.matches(method2));
        }

        {
            Class<?> targetClass = com.lifeiscoding.spring.test.service.PetStoreService.class;

            Method method = targetClass.getMethod("getAccountDao");
            Assert.assertFalse(mm.matches(method));
        }
    }
}
