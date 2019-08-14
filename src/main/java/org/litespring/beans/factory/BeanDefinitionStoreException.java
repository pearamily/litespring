package org.litespring.beans.factory;

import org.litespring.beans.BeanException;

import java.io.IOException;

public class BeanDefinitionStoreException extends BeanException {
    public BeanDefinitionStoreException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
