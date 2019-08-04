package org.litespring.test.v2;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.BeanDefination;
import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.config.TypeStringValue;
import org.litespring.beans.factory.support.BeanDefinationValueResolver;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanFactoryReader;
import org.litespring.core.io.ClassPathResource;
import org.litespring.dao.v2.AccountDao;



public class BeanDefinationValueResolverTest {
    @Test
    public void testResolverRuntimeBeanReference() {

        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanFactoryReader reader = new XmlBeanFactoryReader(factory);

        reader.loadBeanDefination(new ClassPathResource("petstore-v2.xml"));

        BeanDefinationValueResolver resolver = new BeanDefinationValueResolver(factory);
        RuntimeBeanReference reference = new RuntimeBeanReference("accountDao");

        Object value = resolver.resolveValueIfNecessary(reference);
        Assert.assertNotNull(value);
        Assert.assertTrue(value instanceof AccountDao);

    }

    @Test
    public void testResolvedTypeStringValue() {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanFactoryReader reader = new XmlBeanFactoryReader(factory);
        reader.loadBeanDefination(new ClassPathResource("petstore-v2.xml"));

        BeanDefinationValueResolver resolver = new BeanDefinationValueResolver(factory);

        TypeStringValue stringValue = new TypeStringValue("test");
        Object value = resolver.resolveValueIfNecessary(stringValue);
        Assert.assertNotNull(value);
        Assert.assertEquals("test", value);



    }

}
