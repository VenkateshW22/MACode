package org.example;

import java.lang.annotation.*;

// 1. Define the custom annotation
@Retention(RetentionPolicy.RUNTIME) // Make it available at runtime for reflection
@Target(ElementType.METHOD)      // Can be applied to methods
@Documented                      // Include in Javadoc
@interface Loggable {
    // Annotation elements (like method definitions)
    String value() default "Default log message"; // A string element with a default value

    boolean enabled() default true;               // A boolean element
}
