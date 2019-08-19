package org.litespring.beans.factory;

import org.litespring.beans.BeanException;

public class BeanDefinitionStoreException extends BeanException {
    public BeanDefinitionStoreException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public BeanDefinitionStoreException(String msg) {

        super(msg);
    }
}
