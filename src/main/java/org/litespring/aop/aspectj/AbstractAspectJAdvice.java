package org.litespring.aop.aspectj;

import org.aopalliance.intercept.MethodInvocation;
import org.litespring.aop.Advice;
import org.litespring.aop.Pointcut;

import java.lang.reflect.Method;

public abstract class AbstractAspectJAdvice implements Advice {


    protected Method adviceMethod;
    protected AspectJExpressionPointcut pc;
    protected Object adviceObject;


    public AbstractAspectJAdvice(Method adviceMethod, AspectJExpressionPointcut pointcut, Object adviceObject) {
        this.adviceMethod = adviceMethod;
        this.pc = pointcut;
        this.adviceObject = adviceObject;
    }

    public Pointcut getPointCut() {
        return pc;
    }

    public Method getAdviceMethod() {
        return adviceMethod;
    }

    public void invokeAdvideMethod() throws Throwable {
        adviceMethod.invoke(adviceObject);

    }



}
