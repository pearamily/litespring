package org.litespring.beans;

public interface BeanDefination {
    public static final String SCOPE_SINGLETON = "singleton";
    public static final String SCOPE_PROTOTYPE = "singleton";
    public static final String SCOPE_DEFAULT = "";

    public boolean isSingleton();

    public boolean isPrototype();

    String getScope();

    void setScope(String scope);


    String getBeanClassName();

}
