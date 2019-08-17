package org.litespring.test.v5;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.aop.MethodMatcher;
import org.litespring.aop.config.MethodLocationFactoy;
import org.litespring.beans.factory.support.DefaultBeanfactory;
import org.litespring.beans.factory.xml.XmlBeanDefinationReader;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.Resource;
import org.litespring.tx.TransactionManager;

import java.lang.reflect.Method;

public class MethodLocationFactoyTest {


    @Test
    public void testGetMethod() throws Exception {

        DefaultBeanfactory beanfactory = new DefaultBeanfactory();
        XmlBeanDefinationReader reader = new XmlBeanDefinationReader(beanfactory);

        Resource resource = new ClassPathResource("petstore-v5.xml");

        reader.loadBeanDefination(resource);

        MethodLocationFactoy methodLocationFactoy = new MethodLocationFactoy();
        methodLocationFactoy.setTargetBeanName("tx");
        methodLocationFactoy.setMethodName("start");
        methodLocationFactoy.setBeanFactory(beanfactory);

        Method m = methodLocationFactoy.getObject();
        Assert.assertTrue(TransactionManager.class.equals(m.getDeclaringClass()));
        Assert.assertTrue(m.equals((TransactionManager.class.getMethod("start"))));

    }
}
