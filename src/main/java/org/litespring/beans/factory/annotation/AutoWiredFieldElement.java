package org.litespring.beans.factory.annotation;

import org.litespring.beans.factory.BeanCreatationException;
import org.litespring.beans.factory.config.AutoWireCapableBeanFactory;
import org.litespring.beans.factory.config.DependencyDescriptor;
import org.litespring.beans.factory.support.DefaultBeanfactory;
import org.litespring.util.ReflectUtils;

import java.lang.reflect.Field;

public class AutoWiredFieldElement extends InjectionElement {
    boolean required;

    public AutoWiredFieldElement(Field f, boolean required, AutoWireCapableBeanFactory factory) {
        super(f, factory);
        this.required = required;
    }

    public Field getField() {
        return (Field) this.member;
    }

    @Override
    public void inject(Object target) {
        Field field = this.getField();

        try {
            DependencyDescriptor desc = new DependencyDescriptor(field, this.required);

            Object value = factory.resolveDependency(desc
            );

            if (value != null) {
                ReflectUtils.makeAccessible(field);
                field.set(target, value
                );
            }
        } catch (Throwable ex
        ) {
            throw new BeanCreatationException("could not autowire field:" + field, ex
            );
        }
    }
}
