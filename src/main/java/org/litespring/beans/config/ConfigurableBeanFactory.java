package org.litespring.beans.config;

import org.litespring.beans.factory.BeanFactory;
import org.litespring.beans.factory.config.AutoWireCapableBeanFactory;

public interface ConfigurableBeanFactory extends AutoWireCapableBeanFactory {
    void setBeanClassLoader(ClassLoader beanClassLoader);

    ClassLoader getClassLoader();


}
