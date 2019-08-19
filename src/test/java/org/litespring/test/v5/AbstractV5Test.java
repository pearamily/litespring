package org.litespring.test.v5;

import org.litespring.aop.config.AspectInstanceFactory;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.beans.factory.support.DefaultBeanfactory;
import org.litespring.beans.factory.xml.XmlBeanDefinationReader;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.Resource;
import org.litespring.tx.TransactionManager;

import java.lang.reflect.Method;

public    class AbstractV5Test {


    protected BeanFactory getBeanFactory(String configFile) {

        DefaultBeanfactory defaultBeanfactory = new DefaultBeanfactory();

        XmlBeanDefinationReader reader = new XmlBeanDefinationReader(defaultBeanfactory);

        Resource resource = new ClassPathResource(configFile);

        reader.loadBeanDefination(resource);
        return defaultBeanfactory;
    }

    protected Method getAdviceMethod(String methodName) throws  Exception {
        return TransactionManager.class.getMethod(methodName);
    }

    protected AspectInstanceFactory getAspectInstanceFactory(String targetBeanName) {
        AspectInstanceFactory factory = new AspectInstanceFactory();

        factory.setAspectBeanName(targetBeanName);
        return factory;

    }
}
