package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefination;
import org.litespring.beans.factory.BeanCreatationException;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.util.ClassUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultBeanFactory implements BeanFactory, BeanDefinationRegistry {


    private final Map<String, BeanDefination> beanDefinationMap = new ConcurrentHashMap<String, BeanDefination>();


    public DefaultBeanFactory() {

    }

    public BeanDefination getBeanDefination(String beanId) {
        return this.beanDefinationMap.get(beanId);
    }

    public void registerBeanDefination(String id, BeanDefination bd) {
        this.beanDefinationMap.put(id, bd);

    }

    public Object getBean(String beanId) {
        BeanDefination bd = this.getBeanDefination(beanId);
        if (bd == null) {
            throw new BeanCreatationException("BeanDefination dees not exist!");
        }
        ClassLoader c1 = ClassUtils.getDefaultClassLoader();
        String beanClassName = bd.getBeanClassName();

        try {
            Class<?> clz = c1.loadClass(beanClassName);
            //at this place,we default think there is a default constructor of a type
            return clz.newInstance();
        } catch (Exception e) {
            throw new BeanCreatationException("craete bean for" + beanClassName + " failed", e);
        }

    }
}
