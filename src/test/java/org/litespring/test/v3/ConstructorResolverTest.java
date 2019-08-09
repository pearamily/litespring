package org.litespring.test.v3;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.BeanDefination;
import org.litespring.beans.factory.support.ConstructorResolver;
import org.litespring.beans.factory.support.DefaultBeanfactory;
import org.litespring.beans.factory.xml.XmlBeanDefinationReader;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.Resource;
import org.litespring.service.v3.PetStoreService;

public class ConstructorResolverTest {

    @Test
    public void testAutowireConstructor() {
        DefaultBeanfactory factory = new DefaultBeanfactory();
        XmlBeanDefinationReader reader = new XmlBeanDefinationReader(factory);
        Resource resource = new ClassPathResource("petstore-v3.xml");

        reader.loadBeanDefination(resource);

        BeanDefination bd = factory.getBeanDefination("petStore");

        ConstructorResolver resolver = new ConstructorResolver(factory);

        PetStoreService petStore = (PetStoreService) resolver.autowireConstructor(bd);

        //check arg whether is correct  especially -1 or  1
        Assert.assertEquals(1, petStore.getVersion());
        Assert.assertNotNull(petStore.getAccountDao());
        Assert.assertNotNull(petStore.getItemDao());




    }





}
