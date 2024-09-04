package edu.eci.arep.Annotation;

import java.lang.annotation.*;


/**
 * Annotation for component scan
 * @author Andrés Arias
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ComponentScan {

    /**
     * Base package of the component scan
     * @return The base package of the component scan
     */
    String basePackage();

}
