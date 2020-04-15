package com.lifeiscoding.spring.beans.factory.support;

import com.lifeiscoding.spring.beans.BeanDefinition;
import com.lifeiscoding.spring.beans.ConstructorArgument;
import com.lifeiscoding.spring.beans.SimpleTypeConverter;
import com.lifeiscoding.spring.beans.factory.BeanCreationException;
import com.lifeiscoding.spring.beans.factory.config.ConfigurableBeanFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Constructor;
import java.util.List;

public class ConstructorResolver {

    private final Log logger = LogFactory.getLog(getClass());

    private final AbstractBeanFactory factory;

    public ConstructorResolver(AbstractBeanFactory factory) {
        this.factory = factory;
    }

    public Object autowireConstructor(BeanDefinition bd) {
        Constructor<?> constructorToUse = null;

        Object[] argsToUse = null;

        Class<?> beanClass = null;

        try {
            beanClass = this.factory.getBeanClassLoader().loadClass(bd.getBeanClassName());
        } catch (ClassNotFoundException e) {
            throw new BeanCreationException(bd.getId(), "Instantiation of bean failed, can't resolve class", e);
        }

        Constructor<?>[] candidates = beanClass.getConstructors();

        BeanDefinitionValueResolver beanDefinitionValueResolver = new BeanDefinitionValueResolver(this.factory);

        ConstructorArgument constructorArgument = bd.getConstructorArgument();
        SimpleTypeConverter typeConverter = new SimpleTypeConverter();

        for (int i = 0; i < candidates.length; i++) {
            Class<?>[] parameterTypes = candidates[i].getParameterTypes();
            if (parameterTypes.length != constructorArgument.getArgumentCount()) {
                continue;
            }

            argsToUse = new Object[parameterTypes.length];

            boolean result = this.valuesMatchTypes(parameterTypes, constructorArgument.getArgumentValues(), argsToUse, beanDefinitionValueResolver, typeConverter);

            if (result) {
                constructorToUse = candidates[i];
                break;
            }
        }

        if (constructorToUse == null) {
            throw new BeanCreationException(bd.getId(), "can't find a apporiate constructor");
        }

        try {
            return constructorToUse.newInstance(argsToUse);
        } catch (Exception e) {
            throw new BeanCreationException(bd.getId(), "can't find a create instance using " + constructorToUse);
        }
    }

    private boolean valuesMatchTypes(Class<?>[] parameterTypes, List<ConstructorArgument.ValueHolder> argumentValues, Object[] argsToUse, BeanDefinitionValueResolver beanDefinitionValueResolver, SimpleTypeConverter typeConverter) {
        for (int i = 0; i < parameterTypes.length; i++) {
            ConstructorArgument.ValueHolder valueHolder = argumentValues.get(i);
            Object originalValue = valueHolder.getValue();

            try {
                Object resolvedValue = beanDefinitionValueResolver.resolveValueIfNecessary(originalValue);
                Object convertedValue = typeConverter.convertIfNecessary(resolvedValue, parameterTypes[i]);
                argsToUse[i] = convertedValue;
            } catch (Exception e) {
                logger.error(e);
                return false;
            }
        }
        return true;
    }
}
