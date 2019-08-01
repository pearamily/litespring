package org.litespring.beans.factory.support;

import org.litespring.beans.config.SingletonBeanRegistry;
import org.litespring.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSingletonBeanRegistery implements SingletonBeanRegistry {
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<String, Object>(64);


    public void registerSingleton(String beanName, Object singletonObject) {
        Assert.notNull(beanName, "'beanName' must not be null");
        Object oldObject = this.singletonObjects.get(beanName);
        if (oldObject != null) {
            throw new IllegalStateException("Cound not register object [" + singletonObject + "] under bean name'" + beanName + "':there  is already object [" + oldObject + "]");
        }
        this.singletonObjects.put(beanName, singletonObject);
    }

    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }
}
