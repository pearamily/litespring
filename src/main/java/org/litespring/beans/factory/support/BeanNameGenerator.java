package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefination;

public interface BeanNameGenerator {


    String generateBeanName(BeanDefination defination, BeanDefinationRegistry registry);
}
