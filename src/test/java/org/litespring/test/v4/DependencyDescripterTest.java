package org.litespring.test.v4;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.factory.config.DependencyDescriptor;
import org.litespring.beans.factory.support.DefaultBeanfactory;
import org.litespring.beans.factory.xml.XmlBeanDefinationReader;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.Resource;
import org.litespring.dao.v4.AccountDao;
import org.litespring.service.v4.PetStoreService;

import java.lang.reflect.Field;

public class DependencyDescripterTest {
    @Test
    public void testDependencyResolver() throws Exception {


        DefaultBeanfactory factory = new DefaultBeanfactory();
        XmlBeanDefinationReader reader = new XmlBeanDefinationReader(factory);
        Resource resource = new ClassPathResource("petstore-v4.xml");

        reader.loadBeanDefination(resource);

        Field f = PetStoreService.class.getDeclaredField("accountDao");
        DependencyDescriptor descriptor = new DependencyDescriptor(f, true);
        Object o = factory.resolveDependency(descriptor);
        Assert.assertTrue(o instanceof AccountDao);

    }
}
