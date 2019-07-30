package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefination;

public class GenericBeanDefination implements BeanDefination {

    private String id;
    private String className;
    public GenericBeanDefination(String id, String beanClassName) {
        this.id = id;
        this.className = beanClassName;

    }

    public String getBeanClassName() {
        return this.className;
    }

}
