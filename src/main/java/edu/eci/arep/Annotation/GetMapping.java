package edu.eci.arep.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for GetMapping
 * @author Andrés Arias
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GetMapping {

    /**
     * path of the GetMapping
     * @return The path of the GetMapping
     */
    public String path();
}
