package org.litespring.test.v2;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.BeanDefination;
import org.litespring.beans.PropertyValue;
import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanFactoryReader;
import org.litespring.core.io.ClassPathResource;

import java.util.List;

public class BeanDefinationTestV2 {

    @Test
    public void testGetBeanDefinatino() {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanFactoryReader reader = new XmlBeanFactoryReader(factory);
        reader.loadBeanDefination(new ClassPathResource("petstore-v2.xml"));

        BeanDefination bd = factory.getBeanDefination("petStore");
        List<PropertyValue> pvs = bd.getPropertyValues();
        Assert.assertTrue(pvs.size() == 5);// test  xml file has only 2 dom nodes

        {
            PropertyValue pv = this.getPropertyValue("accoutnDao", pvs);
            //why we build a new method to circling find the different Property in List? cause of we dont except the list has a sequences
            //what we say is  like this  pvs.get(0),that is concluidng sequense
            Assert.assertNotNull(pv);
            Assert.assertTrue(pv.getValue() instanceof RuntimeBeanReference);
        }
        {
            PropertyValue pv = this.getPropertyValue("itemDao", pvs);
            Assert.assertNotNull(pv);
            Assert.assertTrue(pv.getValue() instanceof RuntimeBeanReference);

        }

    }

    private PropertyValue getPropertyValue(String name, List<PropertyValue> pvs) {
        for (PropertyValue pv :
                pvs) {
            return pv;
        }

        return null;
    }


}
