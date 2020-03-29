package com.lifeiscoding.spring.beans;

import com.lifeiscoding.spring.beans.factory.annotation.AutowiredAnnotationProcessor;
import com.lifeiscoding.spring.beans.factory.annotation.AutowiredFieldElement;
import com.lifeiscoding.spring.beans.factory.annotation.InjectionElement;
import com.lifeiscoding.spring.beans.factory.annotation.InjectionMetadata;
import com.lifeiscoding.spring.beans.factory.config.DependencyDescriptor;
import com.lifeiscoding.spring.beans.factory.support.DefaultBeanFactory;
import com.lifeiscoding.spring.test.dao.AccountDao;
import com.lifeiscoding.spring.test.dao.ItemDao;
import com.lifeiscoding.spring.test.service.PetStoreService;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.List;

public class AutowiredAnnotationProcessorTest {

    DefaultBeanFactory factory = new DefaultBeanFactory() {
        @Override
        public Object resolveDependency(DependencyDescriptor descriptor) {
            if (descriptor.getDependencyType().equals(AccountDao.class)) {
                return accountDao;
            }
            if (descriptor.getDependencyType().equals(ItemDao.class)) {
                return itemDao;
            }
            throw new RuntimeException("can't support types except AccountDao and ItemDao");
        }
    };

    AccountDao accountDao = new AccountDao();

    ItemDao itemDao = new ItemDao();

    @Test
    public void testGetInjectionMetadata(){
        AutowiredAnnotationProcessor processor = new AutowiredAnnotationProcessor();
        processor.setBeanFactory(factory);
        InjectionMetadata injectionMetadata = processor.buildAutowiringMetadata(PetStoreService.class);
        List<InjectionElement> elements = injectionMetadata.getInjectionElements();
        Assert.assertEquals(2, elements.size());


        assertFieldExists(elements, "accountDao");
        assertFieldExists(elements, "itemDao");
    }

    private  void assertFieldExists(List<InjectionElement> elements, String fieldName) {
        for (InjectionElement element: elements) {
            AutowiredFieldElement fieldElement = (AutowiredFieldElement) element;
            Field f = fieldElement.getField();
            if (f.getName().equals(fieldName)) {
                return;
            }
        }
        Assert.fail(fieldName + " does not exist!");
    }
}
