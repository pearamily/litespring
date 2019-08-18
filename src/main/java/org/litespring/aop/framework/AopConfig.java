package org.litespring.aop.framework;

import org.litespring.aop.Advice;

import java.lang.reflect.Method;
import java.util.List;

public interface AopConfig {

    Class<?> getTargetClass();

    Object getTargetObject();

    ///             

    boolean isProxyTargetClass();

    Class<?>[] getProxiedInterfaces();

    boolean isInterfaceProxied(Class<?> intf);

    ///



    List<Advice> getAdvices();

    void addAdvice(Advice advice);

    List<Advice> getAdvices(Method method);

    void setTargetObject(Object obj);
}
