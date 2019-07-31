package org.litespring.test.v1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.litespring.beans.BeanDefination;
import org.litespring.beans.factory.BeanCreatationException;
import org.litespring.beans.factory.BeanDefinationStoreException;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanFactoryReader;
import org.litespring.core.io.ClassPathResource;
import org.litespring.service.v1.PetStoreService;

import static org.junit.Assert.*;

public class BeanFactoryTest {
    DefaultBeanFactory factory = null;
    XmlBeanFactoryReader reader = null;

    @Before
    public void prepareFactory() {
        factory = new DefaultBeanFactory();
        reader = new XmlBeanFactoryReader(factory);


    }

    @Test
    public void testGetBean() {
        reader.loadBeanDefination(new ClassPathResource("petstore-v1.xml"));

        BeanDefination bd = factory.getBeanDefination("petStore");


        assertEquals("org.litespring.service.v1.PetStoreService", bd.getBeanClassName());

        PetStoreService petStore = (PetStoreService) factory.getBean("petStore");

        assertNotNull(petStore);


    }

    @Test
    public void testInvalidBean() {



        reader.loadBeanDefination(new ClassPathResource("petstore-v1.xml"));

        try {
            factory.getBean("invalidBean");
        } catch (BeanCreatationException e) {
            e.printStackTrace();
            return;
        }
        Assert.fail("except BeanCreatationException");

    }

    @Test
    public void testInvalidXml() {


        try {
            reader.loadBeanDefination(new ClassPathResource("haf.xml"));
        } catch (BeanDefinationStoreException e) {
            return;
        }
        Assert.fail("except BeanDefinationStoreException");
    }
}
