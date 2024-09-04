package edu.eci.arep.Annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for RequestParam
 * @author Andrés Arias
 */
@Target({ElementType.PARAMETER, ElementType.TYPE_PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestParam {

    /**
     * value of the RequestParam
     * @return The value of the RequestParam
     */
    String value();

    /**
     * defaultValue of the RequestParam
     * @return The defaultValue of the RequestParam
     */
    String defaultValue();
}
