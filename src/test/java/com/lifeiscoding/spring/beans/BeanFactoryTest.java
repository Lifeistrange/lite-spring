package com.lifeiscoding.spring.beans;

import com.lifeiscoding.spring.AbstractTest;
import com.lifeiscoding.spring.aop.Advice;
import com.lifeiscoding.spring.aop.aspectj.AspectJAfterReturningAdvice;
import com.lifeiscoding.spring.aop.aspectj.AspectJAfterThrowingAdvice;
import com.lifeiscoding.spring.aop.aspectj.AspectJBeforeAdvice;
import com.lifeiscoding.spring.beans.factory.BeanCreationException;
import com.lifeiscoding.spring.beans.factory.BeanDefinitionStoreException;
import com.lifeiscoding.spring.beans.factory.BeanFactory;
import com.lifeiscoding.spring.beans.factory.support.DefaultBeanFactory;
import com.lifeiscoding.spring.beans.factory.xml.XMLBeanDefinitionReader;
import com.lifeiscoding.spring.core.ClassPathResource;
import com.lifeiscoding.spring.test.service.PetStoreService;
import com.lifeiscoding.spring.test.tx.TransactionManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class BeanFactoryTest extends AbstractTest {

    private DefaultBeanFactory factory;
    private XMLBeanDefinitionReader reader;

    @Before
    public void setUp() {
        this.factory = new DefaultBeanFactory();
        this.reader = new XMLBeanDefinitionReader(factory);
    }

    @Test
    public void testGetBean() {
        reader.loadBeanDefinition(new ClassPathResource("petstore-v1.xml"));
        BeanDefinition bd = factory.getBeanDefinition("petStore");

        assertTrue(bd.isSingleton());
        assertFalse(bd.isPrototype());
        assertEquals(BeanDefinition.SCOPE.DEFAULT, bd.getScope());


        assertEquals("com.lifeiscoding.spring.test.service.PetStoreService", bd.getBeanClassName());

        PetStoreService petStore = (PetStoreService) factory.getBean("petStore");

        assertNotNull(petStore);

        PetStoreService petStore2 = (PetStoreService) factory.getBean("petStore");

        assertEquals(petStore, petStore2);
    }

    @Test
    public void testInvilidBean() {
        reader.loadBeanDefinition(new ClassPathResource("petstore-v1.xml"));
        try {
            factory.getBean("invalidBean");
        } catch (BeanCreationException e) {
            return;
        }
        Assert.fail("except BeanCreationException");
    }

    @Test
    public void testInvalidXML() {
        try {
            reader.loadBeanDefinition(new ClassPathResource("xxx.xml"));
        } catch (BeanDefinitionStoreException e) {
            return;
        }

        Assert.fail("except BeanDefinitionStoreException");
    }

    @Test
    public void testGetBeanByType() throws Exception{
        String expectedExpression = "execution(* com.lifeiscoding.spring.test.service.*.placeOrder(..))";

        BeanFactory factory = super.getBeanFactory("petstore-v5.xml");

        List<Object> advices = factory.getBeansByType(Advice.class);

        Assert.assertEquals(3, advices.size());

        {
            AspectJBeforeAdvice advice = (AspectJBeforeAdvice)this.getAdvice(AspectJBeforeAdvice.class, advices);

            Assert.assertEquals(TransactionManager.class.getMethod("start"), advice.getAdviceMethod());

            Assert.assertEquals(expectedExpression,advice.getPointcut().getExpression());

            Assert.assertEquals(TransactionManager.class,advice.getAdviceInstance().getClass());

        }


        {
            AspectJAfterReturningAdvice advice = (AspectJAfterReturningAdvice)this.getAdvice(AspectJAfterReturningAdvice.class, advices);

            Assert.assertEquals(TransactionManager.class.getMethod("commit"), advice.getAdviceMethod());

            Assert.assertEquals(expectedExpression,advice.getPointcut().getExpression());

            Assert.assertEquals(TransactionManager.class,advice.getAdviceInstance().getClass());

        }

        {
            AspectJAfterThrowingAdvice advice = (AspectJAfterThrowingAdvice)this.getAdvice(AspectJAfterThrowingAdvice.class, advices);

            Assert.assertEquals(TransactionManager.class.getMethod("rollback"), advice.getAdviceMethod());

            Assert.assertEquals(expectedExpression,advice.getPointcut().getExpression());

            Assert.assertEquals(TransactionManager.class,advice.getAdviceInstance().getClass());

        }

    }

    public Object getAdvice(Class<?> type,List<Object> advices){
        for(Object o : advices){
            if(o.getClass().equals(type)){
                return o;
            }
        }
        return null;
    }

}
