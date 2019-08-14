package org.litespring.beans.factory.config;

import org.litespring.beans.factory.BeanFactory;

public interface AutoWireCapableBeanFactory extends BeanFactory {
    public Object resolveDependency(DependencyDescriptor descriptor);
}
