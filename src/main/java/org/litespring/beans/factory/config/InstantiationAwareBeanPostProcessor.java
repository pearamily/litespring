package org.litespring.beans.factory.config;

import org.litespring.beans.BeanException;

public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

    Object beforeInstantiation(Class<?> beanClass, String beanName) throws BeanException;

    boolean afterInstantiation(Object bean, String beanName) throws BeanException;

    void postProcessPropertyValues(Object bean, String beanName) throws BeanException;


}
