package org.litespring.context;

import org.litespring.beans.config.ConfigurableBeanFactory;
import org.litespring.beans.factory.BeanFactory;

public interface ApplicationContext extends ConfigurableBeanFactory {

    Object getBean(String beanId);
}
