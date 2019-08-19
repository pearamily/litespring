package org.litespring.context.annotation;

import org.litespring.beans.factory.annotation.AnnotationBeanDefination;
import org.litespring.beans.factory.support.GenericBeanDefination;
import org.litespring.core.type.AnnotationMetadata;

public class ScanneredGenericBeanDefinition extends GenericBeanDefination implements AnnotationBeanDefination {
    private final AnnotationMetadata metadata;

    public ScanneredGenericBeanDefinition(AnnotationMetadata metadata) {
        super();
        this.metadata = metadata;
        setBeanClassName(this.metadata.getClassName());
    }

    public  final AnnotationMetadata getMetadata() {
        return this.metadata;
    }
}
