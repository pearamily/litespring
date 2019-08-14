package org.litespring.context.annotation;

import org.litespring.beans.BeanDefination;
import org.litespring.beans.factory.annotation.AnnotationBeanDefination;
import org.litespring.beans.factory.support.BeanDefinationRegistry;
import org.litespring.beans.factory.support.BeanNameGenerator;
import org.litespring.core.annotation.AnnotationAttributes;
import org.litespring.core.type.AnnotationMetadata;
import org.litespring.util.ClassUtils;
import org.litespring.util.StringUtils;

import java.beans.Introspector;
import java.util.Set;

public class AnnotationBeanNameGenerator implements BeanNameGenerator {
    public String generateBeanName(BeanDefination defination, BeanDefinationRegistry registry) {
        if (defination instanceof AnnotationBeanDefination) {
            String beanName = determineBeanNameFromAnnotation((AnnotationBeanDefination) defination);

            if (StringUtils.hasText(beanName)) {
                return beanName;
            }
        }

        return buildDefaultBeanName(defination, registry);
    }

    protected String buildDefaultBeanName(BeanDefination defination, BeanDefinationRegistry registry) {
        return buildDefaultBeanName(defination);
    }

    protected String buildDefaultBeanName(BeanDefination defination) {
        String shortClassName = ClassUtils.getShortName(defination.getBeanClassName());
        return Introspector.decapitalize(shortClassName);

    }

    protected String determineBeanNameFromAnnotation(AnnotationBeanDefination annotationBeanDef) {
        AnnotationMetadata amd = annotationBeanDef.getMetadata();

        Set<String> types = amd.getAnnotationTypes();
        String beanName = null;
        for (String type :
                types) {
            AnnotationAttributes attributes = amd.getAnnotationAtrributes(type);
            if (attributes.get("value") != null) {
                Object value = attributes.get("value");
                if (value instanceof String) {
                    String strVal = (String) value;
                    if (StringUtils.hasLength(strVal)) {
                        beanName = strVal;
                    }
                }
            }
        }
        return beanName;


    }
}
