package com.lifeiscoding.spring.core.type.classreading;

import com.lifeiscoding.spring.core.annotation.AnnotationAttributes;
import org.springframework.asm.AnnotationVisitor;
import org.springframework.asm.SpringAsmInfo;

import java.util.Map;

public class AnnotationAttributesReadingVisitor  extends AnnotationVisitor {
    private final String annotationType;

    private final Map<String,AnnotationAttributes> attributesMap;

    AnnotationAttributes annotationAttributes = new AnnotationAttributes();

    public AnnotationAttributesReadingVisitor(String annotationType, Map<String, AnnotationAttributes> attributesMap) {
        super(SpringAsmInfo.ASM_VERSION);
        this.annotationType = annotationType;
        this.attributesMap = attributesMap;
    }

    @Override
    public final void visitEnd() {
        this.attributesMap.put(this.annotationType, this.annotationAttributes);
    }

    public void visit(String attributeName, Object attributeValue) {
        this.annotationAttributes.put(attributeName, attributeValue);
    }
}
