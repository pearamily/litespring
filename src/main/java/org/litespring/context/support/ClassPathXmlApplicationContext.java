package org.litespring.context.support;

import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanFactoryReader;
import org.litespring.context.ApplicationContext;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.Resource;

public class ClassPathXmlApplicationContext implements ApplicationContext {
    private DefaultBeanFactory factory = null;

    public ClassPathXmlApplicationContext(String configFile) {
        factory = new DefaultBeanFactory();

        XmlBeanFactoryReader reader = new XmlBeanFactoryReader(factory);
        Resource resource = new ClassPathResource(configFile);
        reader.loadBeanDefination(resource);

    }

    public Object getBean(String beanId) {
        return factory.getBean(beanId);
    }


}
