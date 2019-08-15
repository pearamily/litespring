package org.litespring.beans.factory.annotation;

import org.litespring.beans.factory.config.AutoWireCapableBeanFactory;

import java.lang.reflect.Member;

public abstract class InjectionElement {
    protected Member member;
    protected AutoWireCapableBeanFactory factory;

    public InjectionElement(Member member, AutoWireCapableBeanFactory factory) {
        this.member = member;
        this.factory = factory;
    }

    public abstract void inject(Object target);



}
