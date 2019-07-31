package org.litespring.context.support;

import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanFactoryReader;
import org.litespring.context.ApplicationContext;

public class ClassPathApplicationContext implements ApplicationContext {
    private DefaultBeanFactory factory = null;

    public ClassPathApplicationContext(String configFile) {
        factory = new DefaultBeanFactory();

        XmlBeanFactoryReader reader = new XmlBeanFactoryReader(factory);
        reader.loadBeanDefination(configFile);

    }

    public Object getBean(String beanId) {
        return factory.getBean(beanId);

    }
}
