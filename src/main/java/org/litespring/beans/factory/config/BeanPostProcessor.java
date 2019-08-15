package org.litespring.beans.factory.config;

import org.litespring.beans.BeanException;
import org.omg.CORBA.OBJ_ADAPTER;

public interface BeanPostProcessor {
    Object beforeInitialization(Object bean, String beanName) throws BeanException;

    Object afterInitialization(Object bean, String beanName) throws BeanException;

}
