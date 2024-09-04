package edu.eci.arep.servers;

import edu.eci.arep.Annotation.ComponentLoader;
import edu.eci.arep.Annotation.GetMapping;
import edu.eci.arep.Annotation.RequestMapping;
import edu.eci.arep.Annotation.RestController;
import edu.eci.arep.App;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * SpringServer Class
 * @author Andrés Arias
 */
public class SpringServer {

    private static SpringServer instance;
    private ComponentLoader components;
    private final Map<String, Method> getRoutes = new HashMap<>();

    /**
     * Private constructor for the SantaSpark class.
     */
    private SpringServer() {
        this.components = new ComponentLoader(App.class);
        saveMappings();
    }

    /**
     * Method to get the instance of the SparkServer class.
     *
     * @return The instance of the SparkServer class.
     */
    public static SpringServer getInstance() {
        if (instance == null) {
            instance = new SpringServer();
        }
        return instance;
    }

    /**
     * Method to get all the methods in the classes that have the annotations requested
     *
     */
    private void saveMappings() {
        for (Class<?> cls : components.getClasscomponents()) {
            if(cls.isAnnotationPresent(RestController.class)){
                for (Method method : cls.getDeclaredMethods()) {
                    String path;
                    if (method.isAnnotationPresent(RequestMapping.class)) {
                        path = method.getAnnotation(RequestMapping.class).path();
                    }else{
                        path = method.getAnnotation(GetMapping.class).path();
                    }
                    getRoutes.put(path,method);
                }
            }
        }
    }

    /**
     * Method to get the method according to a path
     * @param path path to get the method
     * @return method
     */
    public static Method findMappingMethod(String path) {
        Method method = null;
        SpringServer springServer = getInstance();
        if(springServer.getRoutes.containsKey(path)){
            method = springServer.getRoutes.get(path);
        }
        return method;
    }

}
