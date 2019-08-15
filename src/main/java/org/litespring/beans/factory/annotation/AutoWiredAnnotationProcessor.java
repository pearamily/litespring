package org.litespring.beans.factory.annotation;

import org.litespring.beans.BeanException;
import org.litespring.beans.factory.BeanCreatationException;
import org.litespring.beans.factory.config.AutoWireCapableBeanFactory;
import org.litespring.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.litespring.core.annotation.AnnotationUtils;
import org.litespring.util.ReflectUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

public class AutoWiredAnnotationProcessor implements InstantiationAwareBeanPostProcessor {
    private AutoWireCapableBeanFactory beanFactory;
    private String requiredParameterName = "required";
    private boolean requiredParamterValue = true;

    private final Set<Class<? extends Annotation>> autowiredAnnotationTypes = new LinkedHashSet<Class<? extends Annotation>>();

    public AutoWiredAnnotationProcessor() {
        this.autowiredAnnotationTypes.add(Autowired.class);
    }

    public InjectionMetadata buildAutowiringMetadata(Class<?> clazz) {
        LinkedList<InjectionElement> elements = new LinkedList<InjectionElement>();
        Class<?> targetClass = clazz;

        do {
            LinkedList<InjectionElement> currElements = new LinkedList<InjectionElement>();
            for (Field field : targetClass.getDeclaredFields()) {
                Annotation ann = findAutowiredAnnotation(field);
                if (ann != null) {
                    if (Modifier.isStatic(field.getModifiers())) {
                        continue;

                    }
                    boolean required = determineRequiredStatus(ann);
                    currElements.add(new AutoWiredFieldElement(field, required, beanFactory));
                }
            }
            for (Method method : targetClass.getDeclaredMethods()
            ) {
                //TODO  handler injection

            }
            elements.addAll(0, currElements);
            targetClass = targetClass.getSuperclass();
        } while (targetClass != null && targetClass != Object.class);
        return new InjectionMetadata(clazz, elements);
    }

    protected boolean determineRequiredStatus(Annotation ann) {
        Method method = ReflectUtils.findMethod(ann.annotationType(), this.requiredParameterName);
        if (method == null) {
            return true;
        }
        return (this.requiredParamterValue == (Boolean) ReflectUtils.invokeMethod(method, ann));
    }

    private Annotation findAutowiredAnnotation(AccessibleObject ao) {
        for (Class<? extends Annotation> type : this.autowiredAnnotationTypes
        ) {
            Annotation ann = AnnotationUtils.getAnnotation(ao, type);
            if (ann != null) {
                return ann;
            }
        }
        return null;
    }

    public void setBeanFactory(AutoWireCapableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }


    public Object beforeInstantiation(Class<?> beanClass, String beanName) throws BeanException {
        return null;
    }

    public boolean afterInstantiation(Object bean, String beanName) throws BeanException {
        return true;
    }

    public Object beforeInitialization(Object bean, String beanName) throws BeanException {
        return bean;
    }

    public Object afterInitialization(Object bean, String beanName) throws BeanException {
        return bean;
    }

    public void postProcessPropertyValues(Object bean, String beanName) throws BeanException {
        InjectionMetadata metadata = buildAutowiringMetadata(bean.getClass());
        try {
            metadata.inject(bean);
        } catch (Throwable e) {
            throw new BeanCreatationException(beanName, "injection of autowired dependencies failed ");
        }


    }

}
