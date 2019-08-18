package org.litespring.aop.framework;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class ReflectiveMethodInvocation implements MethodInvocation {

    protected final Object targetObject;//petstoreservice
    protected final Method targetMethod;//placeorder()
    private Object[] arguments;

    protected final List<MethodInterceptor> interceptors;//list of intercepters
    private int currentInterceptorIndex = -1;


    public ReflectiveMethodInvocation(Object targetObject, Method targetMethod, Object[] arguments, List<MethodInterceptor> interceptors) {
        this.targetObject = targetObject;
        this.targetMethod = targetMethod;
        this.arguments = arguments;
        this.interceptors = interceptors;
    }


    public final Object getThis() {
        return this.targetObject;
    }

    public final Method getMethod() {
        return this.targetMethod;
    }

    public final Object[] getArguments() {
        return (this.arguments != null ? this.arguments : new Object[0]);
    }


//how they implements thiere sequencens
    public Object proceed() throws Throwable {
        if (this.currentInterceptorIndex == this.interceptors.size() - 1) {
            return invokeJoinPoint();
        }
        this.currentInterceptorIndex++;
        MethodInterceptor interceptor = this.interceptors.get(this.currentInterceptorIndex);
        return interceptor.invoke(this);


    }

    protected Object invokeJoinPoint() throws Throwable {

        return this.targetMethod.invoke(this.targetObject, this.arguments);

    }


    public AccessibleObject getStaticPart() {
        return null;
    }
}
