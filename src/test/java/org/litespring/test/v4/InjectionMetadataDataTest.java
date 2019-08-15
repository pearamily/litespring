package org.litespring.test.v4;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.factory.annotation.AutoWiredFieldElement;
import org.litespring.beans.factory.annotation.InjectionElement;
import org.litespring.beans.factory.annotation.InjectionMetadata;
import org.litespring.beans.factory.support.DefaultBeanfactory;
import org.litespring.beans.factory.xml.XmlBeanDefinationReader;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.Resource;
import org.litespring.dao.v4.AccountDao;
import org.litespring.dao.v4.ItemDao;
import org.litespring.service.v4.PetStoreService;

import java.lang.reflect.Field;
import java.util.LinkedList;

public class InjectionMetadataDataTest {

    @Test
    public  void  testInjection() throws  Exception {


        DefaultBeanfactory factory = new DefaultBeanfactory();

        XmlBeanDefinationReader reader = new XmlBeanDefinationReader(factory);
        Resource resource = new ClassPathResource("petstore-v4.xml");

        reader.loadBeanDefination(resource);

        Class<?> clz = PetStoreService.class;

        LinkedList<InjectionElement> elements = new LinkedList<InjectionElement>();


        {
            Field f = PetStoreService.class.getDeclaredField("accountDao");
            InjectionElement injectionElem = new AutoWiredFieldElement(f, true, factory);
            elements.add(injectionElem);
        }


        {
            Field f = PetStoreService.class.getDeclaredField("itemDao");
            InjectionElement injectionElem = new AutoWiredFieldElement(f, true, factory);
            elements.add(injectionElem);
        }

        InjectionMetadata metadata = new InjectionMetadata(clz, elements);

        PetStoreService petStore = new PetStoreService();

        metadata.inject(petStore);

        Assert.assertTrue(petStore.getAccountDao() instanceof AccountDao);
        Assert.assertTrue(petStore.getItemDao() instanceof ItemDao);



    }
}
