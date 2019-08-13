package org.litespring.core.type.classreading;

import org.litespring.core.io.Resource;
import org.litespring.core.type.AnnotationMetadata;
import org.litespring.core.type.ClassMetadata;

public interface MetadataReader {

    Resource getRessource();

    ClassMetadata getClassMetadata();

    AnnotationMetadata getAnnotationMetadata();
}
