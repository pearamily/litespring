package org.litespring.beans.factory.annotation;

import org.litespring.beans.BeanDefination;
import org.litespring.core.type.AnnotationMetadata;

public interface AnnotationBeanDefination extends BeanDefination {

    AnnotationMetadata getMetadata();


}
