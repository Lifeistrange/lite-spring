package com.lifeiscoding.spring.aop.config;

import com.lifeiscoding.spring.aop.aspectj.AspectJAfterReturningAdvice;
import com.lifeiscoding.spring.aop.aspectj.AspectJAfterThrowingAdvice;
import com.lifeiscoding.spring.aop.aspectj.AspectJBeforeAdvice;
import com.lifeiscoding.spring.aop.aspectj.AspectJExpressionPointcut;
import com.lifeiscoding.spring.beans.BeanDefinition;
import com.lifeiscoding.spring.beans.ConstructorArgument;
import com.lifeiscoding.spring.beans.PropertyValue;
import com.lifeiscoding.spring.beans.factory.config.BeanDefinitionReaderUtils;
import com.lifeiscoding.spring.beans.factory.config.RuntimeBeanReference;
import com.lifeiscoding.spring.beans.factory.support.BeanDefinitionRegistry;
import com.lifeiscoding.spring.beans.factory.support.GenericBeanDefinition;
import com.lifeiscoding.spring.util.StringUtils;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

public class ConfigBeanDefinitionParser {


    private static final String ASPECT = "aspect";
    private static final String EXPRESSION = "expression";
    private static final String ID = "id";
    private static final String POINTCUT = "pointcut";
    private static final String POINTCUT_REF = "pointcut-ref";
    private static final String REF = "ref";
    private static final String BEFORE = "before";
    private static final String AFTER = "after";
    private static final String AFTER_RETURNING_ELEMENT = "after-returning";
    private static final String AFTER_THROWING_ELEMENT = "after-throwing";
    private static final String AROUND = "around";
    private static final String ASPECT_NAME_PROPERTY = "aspectName";

    public BeanDefinition parse(Element element, BeanDefinitionRegistry registry) {

        List<Element> childElts = element.elements();
        for (Element elt: childElts) {
            String localName = elt.getName();
			/*if (POINTCUT.equals(localName)) {
				parsePointcut(elt, registry);
			}*/
			/*else if (ADVISOR.equals(localName)) {
				parseAdvisor(elt, registry);
			}*/
            if (ASPECT.equals(localName)) {
                parseAspect(elt, registry);
            }
        }

        return null;
    }

    private void parseAspect(Element aspectElement, BeanDefinitionRegistry registry) {
        String aspectId = aspectElement.attributeValue(ID);
        String aspectName = aspectElement.attributeValue(REF);


        List<BeanDefinition> beanDefinitions = new ArrayList<BeanDefinition>();
        List<RuntimeBeanReference> beanReferences = new ArrayList<RuntimeBeanReference>();


        List<Element> eleList = aspectElement.elements();
        boolean adviceFoundAlready = false;
        for (int i=0;i<eleList.size();i++) {
            Element ele = eleList.get(i);
            if (isAdviceNode(ele)) {
                if (!adviceFoundAlready) {
                    adviceFoundAlready = true;
                    if (!StringUtils.hasText(aspectName)) {
                        return;
                    }
                    beanReferences.add(new RuntimeBeanReference(aspectName));
                }
                GenericBeanDefinition advisorDefinition = parseAdvice(
                        aspectName, i, aspectElement, ele, registry, beanDefinitions, beanReferences);
                beanDefinitions.add(advisorDefinition);
            }
        }

        List<Element> pointcuts = aspectElement.elements(POINTCUT);
        for (Element pointcutElement : pointcuts) {
            parsePointcut(pointcutElement, registry);
        }



    }


    private boolean isAdviceNode(Element ele) {

        String name = ele.getName();
        return (BEFORE.equals(name) || AFTER.equals(name) || AFTER_RETURNING_ELEMENT.equals(name) ||
                AFTER_THROWING_ELEMENT.equals(name) || AROUND.equals(name));

    }

    private GenericBeanDefinition parseAdvice(
            String aspectName, int order, Element aspectElement, Element adviceElement, BeanDefinitionRegistry registry,
            List<BeanDefinition> beanDefinitions, List<RuntimeBeanReference> beanReferences) {

        GenericBeanDefinition methodDefinition = new GenericBeanDefinition( MethodLocatingFactory.class);
        methodDefinition.getPropertyValues().add(new PropertyValue("targetBeanName", aspectName));
        methodDefinition.getPropertyValues().add(new PropertyValue("methodName", adviceElement.attributeValue("method")));
        methodDefinition.setSynthetic(true);

        // create instance factory definition
        GenericBeanDefinition aspectFactoryDef =
                new GenericBeanDefinition(AspectInstanceFactory.class);
        aspectFactoryDef.getPropertyValues().add(new PropertyValue("aspectBeanName", aspectName));
        aspectFactoryDef.setSynthetic(true);

        // register the pointcut
        GenericBeanDefinition adviceDef = createAdviceDefinition(
                adviceElement, registry, aspectName, order, methodDefinition, aspectFactoryDef,
                beanDefinitions, beanReferences);

        adviceDef.setSynthetic(true);


        // register the final advisor
        BeanDefinitionReaderUtils.registerWithGeneratedName(adviceDef, registry);

        return adviceDef;

    }

    private GenericBeanDefinition createAdviceDefinition(
            Element adviceElement, BeanDefinitionRegistry registry, String aspectName, int order,
            GenericBeanDefinition methodDef, GenericBeanDefinition aspectFactoryDef,
            List<BeanDefinition> beanDefinitions, List<RuntimeBeanReference> beanReferences) {

        GenericBeanDefinition adviceDefinition = new GenericBeanDefinition(getAdviceClass(adviceElement));
        adviceDefinition.getPropertyValues().add(new PropertyValue(ASPECT_NAME_PROPERTY, aspectName));


        ConstructorArgument cav = adviceDefinition.getConstructorArgument();
        cav.addArgumentValue(methodDef);

        Object pointcut = parsePointcutProperty(adviceElement);
        if (pointcut instanceof BeanDefinition) {
            cav.addArgumentValue(pointcut);

            beanDefinitions.add((BeanDefinition) pointcut);
        }
        else if (pointcut instanceof String) {
            RuntimeBeanReference pointcutRef = new RuntimeBeanReference((String) pointcut);
            cav.addArgumentValue(pointcutRef);
            beanReferences.add(pointcutRef);
        }
        cav.addArgumentValue(aspectFactoryDef);

        return adviceDefinition;
    }

    private Class<?> getAdviceClass(Element adviceElement) {
        String elementName = adviceElement.getName();
        if (BEFORE.equals(elementName)) {
            return AspectJBeforeAdvice.class;
        }
		/*else if (AFTER.equals(elementName)) {
			return AspectJAfterAdvice.class;
		}*/
        else if (AFTER_RETURNING_ELEMENT.equals(elementName)) {
            return AspectJAfterReturningAdvice.class;
        }
        else if (AFTER_THROWING_ELEMENT.equals(elementName)) {
            return AspectJAfterThrowingAdvice.class;
        }
		/*else if (AROUND.equals(elementName)) {
			return AspectJAroundAdvice.class;
		}*/
        else {
            throw new IllegalArgumentException("Unknown advice kind [" + elementName + "].");
        }
    }

    private GenericBeanDefinition parsePointcut(Element pointcutElement,BeanDefinitionRegistry registry) {
        String id = pointcutElement.attributeValue(ID);
        String expression = pointcutElement.attributeValue(EXPRESSION);

        GenericBeanDefinition pointcutDefinition = null;


        //this.parseState.push(new PointcutEntry(id));
        pointcutDefinition = createPointcutDefinition(expression);
        //pointcutDefinition.setSource(parserContext.extractSource(pointcutElement));

        String pointcutBeanName = id;
        if (StringUtils.hasText(pointcutBeanName)) {
            registry.registerBeanDefinition(pointcutBeanName, pointcutDefinition);
        }
        else {
            BeanDefinitionReaderUtils.registerWithGeneratedName(pointcutDefinition, registry);

        }


        return pointcutDefinition;
    }

    private Object parsePointcutProperty(Element element/*, ParserContext parserContext*/) {
        if ((element.attribute(POINTCUT)== null) && (element.attribute(POINTCUT_REF)==null)) {
			/*parserContext.getReaderContext().error(
					"Cannot define both 'pointcut' and 'pointcut-ref' on <advisor> tag.",
					element, this.parseState.snapshot());*/
            return null;
        }
        else if (element.attribute(POINTCUT)!=null) {
            // Create a pointcut for the anonymous pc and register it.
            String expression = element.attributeValue(POINTCUT);
            GenericBeanDefinition pointcutDefinition = createPointcutDefinition(expression);
            //pointcutDefinition.setSource(parserContext.extractSource(element));
            return pointcutDefinition;
        }
        else if (element.attribute(POINTCUT_REF)!=null) {
            String pointcutRef = element.attributeValue(POINTCUT_REF);
            if (!StringUtils.hasText(pointcutRef)) {
				/*parserContext.getReaderContext().error(
						"'pointcut-ref' attribute contains empty value.", element, this.parseState.snapshot());*/
                return null;
            }
            return pointcutRef;
        }
        else {/*
			parserContext.getReaderContext().error(
					"Must define one of 'pointcut' or 'pointcut-ref' on <advisor> tag.",
					element, this.parseState.snapshot());*/
            return null;
        }
    }

    protected GenericBeanDefinition createPointcutDefinition(String expression) {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition(AspectJExpressionPointcut.class);
        beanDefinition.setScope(BeanDefinition.SCOPE.PROTOTYPE);
        beanDefinition.setSynthetic(true);
        beanDefinition.getPropertyValues().add(new PropertyValue(EXPRESSION, expression));
        return beanDefinition;
    }

}
