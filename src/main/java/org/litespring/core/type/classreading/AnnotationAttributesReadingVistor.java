package org.litespring.core.type.classreading;

import org.litespring.core.annotation.AnnotationAttributes;
import org.springframework.asm.AnnotationVisitor;
import org.springframework.asm.SpringAsmInfo;

import java.util.Map;

public class AnnotationAttributesReadingVistor extends AnnotationVisitor {

    private final String annotationType;
    private final Map<String, AnnotationAttributes> attributesMap;

    AnnotationAttributes attributes = new AnnotationAttributes();


    public AnnotationAttributesReadingVistor(String annotationType, Map<String, AnnotationAttributes> attributesMap) {

        super(SpringAsmInfo.ASM_VERSION);
        this.annotationType = annotationType;
        this.attributesMap = attributesMap;
    }

    @Override
    public void visit(String attributeName, Object attributeValue) {

        this.attributes.put(attributeName, attributeValue);
    }

    @Override
    public void visitEnd() {
        this.attributesMap.put(this.annotationType, this.attributes);
    }
}
