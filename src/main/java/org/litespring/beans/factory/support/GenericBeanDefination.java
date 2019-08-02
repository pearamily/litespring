package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefination;
import org.litespring.beans.PropertyValue;

import java.util.ArrayList;
import java.util.List;

public class GenericBeanDefination implements BeanDefination {

    private String id;
    private String className;

    private boolean singleton = true;
    private boolean prototype = false;
    private String scope = SCOPE_DEFAULT;

    List<PropertyValue> propertyValues = new ArrayList<PropertyValue>();


    public GenericBeanDefination(String id, String beanClassName) {
        this.id = id;
        this.className = beanClassName;

    }


    public String getBeanClassName() {
        return this.className;
    }

    public List<PropertyValue> getPropertyValues() {
        return propertyValues;
    }

    public boolean isSingleton() {
        return this.singleton;
    }

    public boolean isPrototype() {
        return this.prototype;
    }

    public String getScope() {
        return this.scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
        this.singleton = SCOPE_SINGLETON.equals(scope) || SCOPE_DEFAULT.equals(scope);
        this.prototype = SCOPE_PROTOTYPE.equals(scope);

    }
}
