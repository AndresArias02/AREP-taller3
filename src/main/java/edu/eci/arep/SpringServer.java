package edu.eci.arep;

import edu.eci.arep.Annotation.ComponentLoader;
import edu.eci.arep.Annotation.GetMapping;
import edu.eci.arep.Annotation.RequestMapping;
import edu.eci.arep.Annotation.RestController;
import edu.eci.arep.Enum.HttpMethod;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class SpringServer {

    private static SpringServer instance;
    private ComponentLoader components;
    private final Map<String, Method> getRoutes = new HashMap<>();


    private SpringServer() {
        this.components = new ComponentLoader(App.class);
        saveMappings();
    }


    public static SpringServer getInstance() {
        if (instance == null) {
            instance = new SpringServer();
        }
        return instance;
    }

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

    public static Method findMappingMethod(String path) {
        Method method = null;
        SpringServer springServer = getInstance();
        if(springServer.getRoutes.containsKey(path)){
            method = springServer.getRoutes.get(path);
        }
        return method;
    }

}
