package org.litespring.context.support;

import org.litespring.beans.config.ConfigurableBeanFactory;
import org.litespring.beans.factory.NoSuchBeanDefinationException;
import org.litespring.beans.factory.annotation.AutoWiredAnnotationProcessor;
import org.litespring.beans.factory.support.DefaultBeanfactory;
import org.litespring.beans.factory.xml.XmlBeanDefinationReader;
import org.litespring.context.ApplicationContext;
import org.litespring.core.io.Resource;
import org.litespring.util.ClassUtils;

public abstract class AbstractApplicationContext implements ApplicationContext {

    private DefaultBeanfactory factory = null;
    private ClassLoader beanClassLoader;

    //now lets deal with classloader
    public AbstractApplicationContext(String configFile) {
        factory = new DefaultBeanfactory();

        XmlBeanDefinationReader reader = new XmlBeanDefinationReader(factory);

        Resource resource = this.getResourceByPath(configFile);

        reader.loadBeanDefination(resource);

        factory.setBeanClassLoader(this.getClassLoader());

        registerBeanPostProcessor(factory);

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

    protected void registerBeanPostProcessor(ConfigurableBeanFactory beanFactory) {
        AutoWiredAnnotationProcessor postProcessor = new AutoWiredAnnotationProcessor();
        postProcessor.setBeanFactory(beanFactory);
        beanFactory.addBeanPostProcessor(postProcessor);

    }

    public  Class<?> getType(String name) throws NoSuchBeanDefinationException {
        return this.factory.getType(name);
    }

}
