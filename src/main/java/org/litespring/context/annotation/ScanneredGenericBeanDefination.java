package org.litespring.context.annotation;

import org.litespring.beans.factory.annotation.AnnotationBeanDefination;
import org.litespring.beans.factory.support.GenericBeanDefination;
import org.litespring.core.type.AnnotationMetadata;

public class ScanneredGenericBeanDefination extends GenericBeanDefination implements AnnotationBeanDefination {
    private final AnnotationMetadata metadata;

    public ScanneredGenericBeanDefination(AnnotationMetadata metadata) {
        super();
        this.metadata = metadata;
        setBeanClassName(this.metadata.getClassName());
    }

    public  final AnnotationMetadata getMetadata() {
        return this.metadata;
    }
}
