package org.example;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
// 1. Define the custom annotation
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE) // Can be applied to classes/interfaces
@interface ServiceInfo {
    String name();

    String version() default "1.0.0";

    String[] authors() default {}; // An array element
}
