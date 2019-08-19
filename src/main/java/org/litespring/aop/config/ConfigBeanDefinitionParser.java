package org.litespring.aop.config;



import org.dom4j.Element;
import org.litespring.aop.aspectj.AspectJAfterReturningAdvice;
import org.litespring.aop.aspectj.AspectJAfterThrowingAdvice;
import org.litespring.aop.aspectj.AspectJBeforeAdvice;
import org.litespring.aop.aspectj.AspectJExpressionPointcut;
import org.litespring.beans.BeanDefination;
import org.litespring.beans.ConstructorArgument;
import org.litespring.beans.PropertyValue;
import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.support.BeanDefinitionReaderUtils;
import org.litespring.beans.factory.support.BeanDefinationRegistry;
import org.litespring.beans.factory.support.GenericBeanDefination;
import org.litespring.util.StringUtils;

import java.util.ArrayList;
import java.util.List;




public class ConfigBeanDefinitionParser /*implements BeanDefinitionParser*/ {

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

    public BeanDefination parse(Element element,BeanDefinationRegistry registry) {

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



    private void parseAspect(Element aspectElement, BeanDefinationRegistry registry) {
        String aspectId = aspectElement.attributeValue(ID);
        String aspectName = aspectElement.attributeValue(REF);


        List<BeanDefination> beanDefinitions = new ArrayList<BeanDefination>();
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
                GenericBeanDefination advisorDefinition = parseAdvice(
                        aspectName, i, aspectElement, ele, registry, beanDefinitions, beanReferences);
                beanDefinitions.add(advisorDefinition);
            }
        }

        List<Element> pointcuts = aspectElement.elements(POINTCUT);
        for (Element pointcutElement : pointcuts) {
            parsePointcut(pointcutElement, registry);
        }



    }


    /**
     * Return {@code true} if the supplied node describes an advice type. May be one of:
     * '{@code before}', '{@code after}', '{@code after-returning}',
     * '{@code after-throwing}' or '{@code around}'.
     */
    private boolean isAdviceNode(Element ele) {

        String name = ele.getName();
        return (BEFORE.equals(name) || AFTER.equals(name) || AFTER_RETURNING_ELEMENT.equals(name) ||
                AFTER_THROWING_ELEMENT.equals(name) || AROUND.equals(name));

    }


    private GenericBeanDefination parseAdvice(
            String aspectName, int order, Element aspectElement, Element adviceElement, BeanDefinationRegistry registry,
            List<BeanDefination> beanDefinitions, List<RuntimeBeanReference> beanReferences) {

        GenericBeanDefination methodDefinition = new GenericBeanDefination(MethodLocationFactoy.class);
        methodDefinition.getPropertyValues().add(new PropertyValue("targetBeanName", aspectName));
        methodDefinition.getPropertyValues().add(new PropertyValue("methodName", adviceElement.attributeValue("method")));
        methodDefinition.setSynthetic(true);

        // create instance factory definition
        GenericBeanDefination aspectFactoryDef =
                new GenericBeanDefination(AspectInstanceFactory.class);
        aspectFactoryDef.getPropertyValues().add(new PropertyValue("aspectBeanName", aspectName));
        aspectFactoryDef.setSynthetic(true);

        // register the pointcut
        GenericBeanDefination adviceDef = createAdviceDefinition(
                adviceElement, registry, aspectName, order, methodDefinition, aspectFactoryDef,
                beanDefinitions, beanReferences);

        adviceDef.setSynthetic(true);


        // register the final advisor
        BeanDefinitionReaderUtils.registerWithGeneratedName(adviceDef, registry);

        return adviceDef;

    }

    /**
     * Creates the RootBeanDefinition for a POJO advice bean. Also causes pointcut
     * parsing to occur so that the pointcut may be associate with the advice bean.
     * This same pointcut is also configured as the pointcut for the enclosing
     * Advisor definition using the supplied MutablePropertyValues.
     */
    private GenericBeanDefination createAdviceDefinition(
            Element adviceElement, BeanDefinationRegistry registry, String aspectName, int order,
            GenericBeanDefination methodDef, GenericBeanDefination aspectFactoryDef,
            List<BeanDefination> beanDefinitions, List<RuntimeBeanReference> beanReferences) {

        GenericBeanDefination adviceDefinition = new GenericBeanDefination(getAdviceClass(adviceElement));
        adviceDefinition.getPropertyValues().add(new PropertyValue(ASPECT_NAME_PROPERTY, aspectName));


        ConstructorArgument cav = adviceDefinition.getConstructorArgument();
        cav.addArgumentValue(methodDef);

        Object pointcut = parsePointcutProperty(adviceElement);
        if (pointcut instanceof BeanDefination) {
            cav.addArgumentValue(pointcut);

            beanDefinitions.add((BeanDefination) pointcut);
        }
        else if (pointcut instanceof String) {
            RuntimeBeanReference pointcutRef = new RuntimeBeanReference((String) pointcut);
            cav.addArgumentValue(pointcutRef);
            beanReferences.add(pointcutRef);
        }
        cav.addArgumentValue(aspectFactoryDef);

        return adviceDefinition;
    }

    /**
     * Gets the advice implementation class corresponding to the supplied {@link Element}.
     */
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

    /**
     * Parses the supplied {@code &lt;pointcut&gt;} and registers the resulting
     * Pointcut with the BeanDefinationRegistry.
     */
    private GenericBeanDefination parsePointcut(Element pointcutElement,BeanDefinationRegistry registry) {
        String id = pointcutElement.attributeValue(ID);
        String expression = pointcutElement.attributeValue(EXPRESSION);

        GenericBeanDefination pointcutDefinition = null;


        //this.parseState.push(new PointcutEntry(id));
        pointcutDefinition = createPointcutDefinition(expression);
        //pointcutDefinition.setSource(parserContext.extractSource(pointcutElement));

        String pointcutBeanName = id;
        if (StringUtils.hasText(pointcutBeanName)) {
            registry.registerBeanDefination(pointcutBeanName, pointcutDefinition);
        }
        else {
            BeanDefinitionReaderUtils.registerWithGeneratedName(pointcutDefinition, registry);

        }


        return pointcutDefinition;
    }

    /**
     * Parses the {@code pointcut} or {@code pointcut-ref} attributes of the supplied
     * {@link Element} and add a {@code pointcut} property as appropriate. Generates a
     * {@link org.springframework.beans.factory.config.BeanDefination} for the pointcut if  necessary
     * and returns its bean name, otherwise returns the bean name of the referred pointcut.
     */
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
            GenericBeanDefination pointcutDefinition = createPointcutDefinition(expression);
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

    /**
     * Creates a {@link BeanDefination} for the {@link AspectJExpressionPointcut} class using
     * the supplied pointcut expression.
     */
    protected GenericBeanDefination createPointcutDefinition(String expression) {
        GenericBeanDefination beanDefinition = new GenericBeanDefination(AspectJExpressionPointcut.class);
        beanDefinition.setScope(BeanDefination.SCOPE_PROTOTYPE);
        beanDefinition.setSynthetic(true);
        beanDefinition.getPropertyValues().add(new PropertyValue(EXPRESSION, expression));
        return beanDefinition;
    }

}
