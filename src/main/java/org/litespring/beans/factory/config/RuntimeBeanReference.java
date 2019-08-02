package org.litespring.beans.factory.config;

public class RuntimeBeanReference {
    //this type is used in recalled the definatino
    private final String beanName;

    public RuntimeBeanReference(String beanName) {
        this.beanName = beanName;

    }

    public String getBeanName() {
        return beanName;
    }
}
