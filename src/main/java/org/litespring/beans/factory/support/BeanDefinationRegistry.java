package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefination;

public interface BeanDefinationRegistry {
        //this interface is related to  implement-->get  beanDefination

     BeanDefination getBeanDefination(String beanId);

    void registerBeanDefination(String id, BeanDefination beanDefination);

}
