package org.litespring.beans.factory.annotation;

import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.CONSTRUCTOR,ElementType.ANNOTATION_TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {


    boolean required() default false;



}
