package org.litespring.beans;

import java.util.List;

public interface BeanDefination {
    public static final String SCOPE_SINGLETON = "singleton";
    public static final String SCOPE_PROTOTYPE = "prototype";
    public static final String SCOPE_DEFAULT = "";

    public boolean isSingleton();

    public boolean isPrototype();

    String getScope();

    void setScope(String scope);


    String getBeanClassName();

    public List<PropertyValue> getPropertyValues();

    public ConstructorArgument getConstructorArtgument();

    public String getId();

    ConstructorArgument getConstructorArgument();

    public boolean hasConstructorArgumentValues();

    public boolean hasBeanClasss();

    public Class<?> getBeanClass();

    public Class<?> resolveBeanClass(ClassLoader classLoader) throws ClassNotFoundException;

    boolean isSynthetic();
}

