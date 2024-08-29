package org.example;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Spring {
    public static void main(String[] args) throws ClassNotFoundException, MalformedURLException, InvocationTargetException, IllegalAccessException {
        Class c = Class.forName(args[0]);
        Map<String,Method> services = new HashMap<>();
        Method[] methods;

        //Uploading components
        if(c.isAnnotationPresent(RestController.class)){
            methods = c.getDeclaredMethods();
            for(Method m : methods){
                if(m.isAnnotationPresent(GetMapping.class)){
                    String key = m.getAnnotation(GetMapping.class).value();
                    services.put(key,m);
                }
            }
        }

        //burned code for a example practice
        URL serviceUrl = new URL("http://localhost:8080/App/hello");
        String path = serviceUrl.getPath();
        System.out.println("PATH ------->" + path);

        String serviceName = path.substring(4);
        System.out.println("Service Name ------> " + serviceName);

        Method m = services.get(serviceName);
        System.out.println(m.invoke(null));
    }
}