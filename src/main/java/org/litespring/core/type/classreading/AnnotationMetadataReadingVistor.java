package org.litespring.core.type.classreading;

import com.sun.tools.classfile.AnnotationDefault_attribute;
import jdk.internal.org.objectweb.asm.Type;
import org.litespring.core.annotation.AnnotationAttributes;
import org.litespring.core.type.AnnotationMetadata;
import org.springframework.asm.AnnotationVisitor;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public  class AnnotationMetadataReadingVistor extends ClassMetaDataReadingVisitor implements AnnotationMetadata {
        // overide vistor method of ClassMetaDataReadingVisitor

    private final Set<String> annotationSet = new LinkedHashSet<String>(4);

    private final Map<String, AnnotationAttributes> attributesMap = new LinkedHashMap<String, AnnotationAttributes>(4);


    public AnnotationMetadataReadingVistor() {

    }

    @Override
    public AnnotationVisitor visitAnnotation(final String desc, boolean visble) {
        String className = Type.getType(desc).getClassName();

        this.annotationSet.add(className);

        return new AnnotationAttributesReadingVistor(className, this.attributesMap);
    }

    public Set<String> getAnnotationTypes() {
        return this.annotationSet;
    }

    public boolean hasAnnotation(String annotationType) {
        return this.annotationSet.contains(annotationType);
    }

    public AnnotationAttributes getAnnotationAtrributes(String annotationType) {
        return this.attributesMap.get(annotationType);
    }



}

