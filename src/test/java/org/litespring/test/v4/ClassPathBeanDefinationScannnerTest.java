package org.litespring.test.v4;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.BeanDefination;
import org.litespring.beans.factory.support.DefaultBeanfactory;
import org.litespring.context.annotation.ClassPathBeanDefinationScanner;
import org.litespring.context.annotation.ScanneredGenericBeanDefination;
import org.litespring.core.annotation.AnnotationAttributes;
import org.litespring.core.type.AnnotationMetadata;
import org.litespring.stereotype.Component;

public class ClassPathBeanDefinationScannnerTest {
    @Test
    public void testParseScannedBean() {
        DefaultBeanfactory factory = new DefaultBeanfactory();

        String basePath = "org.litespring.service.v4,org.litespring.dao.v4";

        ClassPathBeanDefinationScanner scanner = new ClassPathBeanDefinationScanner(factory);

        scanner.doScan(basePath);

        String annotation = Component.class.getName();

        {
            BeanDefination bd = factory.getBeanDefination("petStore");
            Assert.assertTrue(bd instanceof ScanneredGenericBeanDefination);
            ScanneredGenericBeanDefination sbd = (ScanneredGenericBeanDefination) bd;
            AnnotationMetadata amd = sbd.getMetadata();

            Assert.assertTrue(amd.hasAnnotation(annotation));
            AnnotationAttributes attributes = amd.getAnnotationAtrributes(annotation);

            Assert.assertEquals("petStore", attributes.get("value"));
        }

        {
            BeanDefination bd = factory.getBeanDefination("accountDao");
            Assert.assertTrue(bd instanceof ScanneredGenericBeanDefination);
            ScanneredGenericBeanDefination sbd = (ScanneredGenericBeanDefination) bd;
            AnnotationMetadata amd = sbd.getMetadata();
            Assert.assertTrue(amd.hasAnnotation(annotation));
        }
        {
            BeanDefination bd = factory.getBeanDefination("itemDao");
            Assert.assertTrue(bd instanceof ScanneredGenericBeanDefination);
            ScanneredGenericBeanDefination sbd = (ScanneredGenericBeanDefination) bd;
            AnnotationMetadata amd = sbd.getMetadata();
            Assert.assertTrue(amd.hasAnnotation(annotation));
        }





    }


}
