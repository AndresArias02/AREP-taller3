package edu.eci.arep.Annotation;



import edu.eci.arep.Enum.HttpMethod;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  Annotation for mapping web requests
 * @author Andrés Arias
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {

    /**
     * Path of the request
     * @return The path of the request
     */
    String path();

    /**
     * Http Method of the request
     * @return The method of the request
     */
    HttpMethod method();
}
