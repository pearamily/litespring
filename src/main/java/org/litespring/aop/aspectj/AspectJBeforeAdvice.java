package org.litespring.aop.aspectj;

import org.aopalliance.intercept.MethodInvocation;
import org.litespring.aop.Advice;
import org.litespring.aop.Pointcut;

import java.lang.reflect.Method;

public class AspectJBeforeAdvice extends AbstractAspectJAdvice {


    public AspectJBeforeAdvice(Method adviceMethod, AspectJExpressionPointcut pointcut, Object adviceObject) {
        super(adviceMethod,pointcut,adviceObject);
    }

    public Object invoke(MethodInvocation mi) throws  Throwable {
        this.invokeAdvideMethod();
        Object o = mi.proceed();
        return o;
    }



}
