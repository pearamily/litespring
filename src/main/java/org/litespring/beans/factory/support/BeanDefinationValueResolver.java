package org.litespring.beans.factory.support;

import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.config.TypeStringValue;

public class BeanDefinationValueResolver {

    private final DefaultBeanFactory beanFactory;

    public BeanDefinationValueResolver(DefaultBeanFactory factory) {
        this.beanFactory = factory;
    }

    public Object resolveValueIfNecessary(Object value) {
        //look and have a try if this value whether should be construct a  new  object
        //so it called  if necessary
        if (value instanceof RuntimeBeanReference) {
            RuntimeBeanReference ref = (RuntimeBeanReference) value;
            String refName = ref.getBeanName();
            Object bean = this.beanFactory.getBean(refName);
            return bean;
        } else if (value instanceof TypeStringValue) {
            return ((TypeStringValue) value).getValue();
        } else {
            //TODO there will be a new Type in future
            throw new RuntimeException("this value :" + value + " has not be implemented");
        }




    }
}
