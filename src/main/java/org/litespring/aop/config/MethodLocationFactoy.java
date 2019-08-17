package org.litespring.aop.config;

import org.litespring.beans.BeanUtils;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.util.StringUtils;

import java.lang.reflect.Method;

public class MethodLocationFactoy {

    private String targetBeanName;
    private String methodName;
    private Method method;


    public void setTargetBeanName(String targetBeanName) {
        this.targetBeanName = targetBeanName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setBeanFactory(BeanFactory factory) {
        if (!StringUtils.hasText(this.targetBeanName)) {
            throw new IllegalArgumentException("Property 'targetBeanName' is required");

        }
        if (!StringUtils.hasText(this.methodName)) {
            throw new IllegalArgumentException("Property 'methodName' is required");

        }

        Class<?> beanClass = factory.getType(this.targetBeanName);
        if (beanClass == null) {
            throw new IllegalArgumentException("Property 'methodName' is required");

        }

        this.method = BeanUtils.resolveSignature(this.methodName, beanClass);
        if (this.method == null) {
            throw new IllegalArgumentException("Unable to locate method [" + this.methodName +
                    "] on bean [" + this.targetBeanName + "]");
        }



    }

    public Method getObject() throws  Exception {
        return this.method;
    }

}

