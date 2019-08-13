package org.litespring.test.v4;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.core.annotation.AnnotationAttributes;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.type.classreading.AnnotationMetadataReadingVistor;
import org.litespring.core.type.classreading.ClassMetaDataReadingVisitor;
import org.springframework.asm.ClassReader;

import java.io.IOException;

public class ClassReaderTest {

    @Test
    public void testClassMetaData() throws IOException {

        ClassPathResource resource = new ClassPathResource("org/litespring/service/v4/PetStoreService.class");
        ClassReader reader = new ClassReader(resource.getInputStream());

        ClassMetaDataReadingVisitor visitor = new ClassMetaDataReadingVisitor();

        reader.accept(visitor, ClassReader.SKIP_DEBUG);

        Assert.assertFalse(visitor.isAbstract());
        Assert.assertFalse(visitor.isInterface());
        Assert.assertFalse(visitor.isFinal());

        Assert.assertEquals("org.litespring.service.v4.PetStoreService", visitor.getClassName());
        Assert.assertEquals("java.lang.Object", visitor.getSuperClassName());
        Assert.assertEquals(0, visitor.getInterfaces().length);


    }

    //why we did test like this ?why we dont use refletcion instead ? //cause annotation
    @Test
    public void testGetAnnotation() throws Exception {
        ClassPathResource resource = new ClassPathResource("org/litespring/service/v4/PetStoreService.class");
        ClassReader reader = new ClassReader(resource.getInputStream());

        AnnotationMetadataReadingVistor visitor = new AnnotationMetadataReadingVistor();

        reader.accept(visitor,ClassReader.SKIP_DEBUG);

        String annotation = "org.litespring.stereotype.Component";
        Assert.assertTrue(visitor.hasAnnotation(annotation));

        AnnotationAttributes attributes = visitor.getAnnotationAtrributes(annotation);

        Assert.assertEquals("petStore", attributes.get("value"));

    }



}
