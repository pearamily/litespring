package org.litespring.beans.factory;

public interface BeanFactory {

    Object getBean(String beanId);

    Class<?> getType(String name) throws  NoSuchBeanDefinationException;
}
