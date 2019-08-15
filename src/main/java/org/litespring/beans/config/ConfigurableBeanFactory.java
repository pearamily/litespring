package org.litespring.beans.config;

import org.litespring.beans.factory.BeanFactory;
import org.litespring.beans.factory.config.AutoWireCapableBeanFactory;
import org.litespring.beans.factory.config.BeanPostProcessor;

import java.util.List;

public interface ConfigurableBeanFactory extends AutoWireCapableBeanFactory {
    void setBeanClassLoader(ClassLoader beanClassLoader);

    ClassLoader getClassLoader();

    void addBeanPostProcessor(BeanPostProcessor postProcessor);

    List<BeanPostProcessor> getBeanPostProcessor();




}
