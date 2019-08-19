package org.litespring.beans.factory;

import org.litespring.beans.BeanException;

public interface BeanFactoryAware {

    void setBeanFactory(BeanFactory beanFactory) throws BeanException;
}
