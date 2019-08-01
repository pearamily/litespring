package org.litespring.context.support;

import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanFactoryReader;
import org.litespring.context.ApplicationContext;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.Resource;
import org.litespring.util.ClassUtils;

public abstract class AbstractApplicationContext implements ApplicationContext {

    private DefaultBeanFactory factory = null;
    private ClassLoader beanClassLoader;

    //now lets deal with classloader
    public AbstractApplicationContext(String configFile) {
        factory = new DefaultBeanFactory();

        XmlBeanFactoryReader reader = new XmlBeanFactoryReader(factory);

        Resource resource = this.getResourceByPath(configFile);

        reader.loadBeanDefination(resource);

        factory.setBeanClassLoader(this.getClassLoader());

    }

    public Object getBean(String beanId) {
        return factory.getBean(beanId);
    }

    protected abstract Resource getResourceByPath(String path);



    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader = beanClassLoader;

    }

    public ClassLoader getClassLoader() {
        return (this.beanClassLoader != null)?this.beanClassLoader: ClassUtils.getDefaultClassLoader();
    }
}
