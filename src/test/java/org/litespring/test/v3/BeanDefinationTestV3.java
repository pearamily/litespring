package org.litespring.test.v3;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.BeanDefination;
import org.litespring.beans.ConstructorArgument;
import org.litespring.beans.ConstructorArgument.ValueHolder;
import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.config.TypeStringValue;
import org.litespring.beans.factory.support.DefaultBeanfactory;
import org.litespring.beans.factory.xml.XmlBeanDefinationReader;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.Resource;

import java.util.List;

public class BeanDefinationTestV3 {

    @Test
    public void testConstructorArgument() {
        DefaultBeanfactory factory = new DefaultBeanfactory();
        XmlBeanDefinationReader reader = new XmlBeanDefinationReader(factory);

        Resource resource = new ClassPathResource("petstore-v3.xml");
        reader.loadBeanDefination(resource);


        BeanDefination bd = factory.getBeanDefination("petStore");
        Assert.assertEquals("org.litespring.service.v3.PetStoreService", bd.getBeanClassName());

        ConstructorArgument args = bd.getConstructorArgument();
        List<ValueHolder> valueHolders = args.getArgumentValues();

        Assert.assertEquals(3, valueHolders.size());


        RuntimeBeanReference ref1  = (RuntimeBeanReference)valueHolders.get(0).getValue();
        Assert.assertEquals("accountDao", ref1.getBeanName());
        RuntimeBeanReference ref2 = (RuntimeBeanReference) valueHolders.get(1).getValue();
        Assert.assertEquals("itemDao", ref2.getBeanName());

        TypeStringValue stringValue = (TypeStringValue) valueHolders.get(2).getValue();
        Assert.assertEquals("1", stringValue.getValue());


    }


}
