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
        Map<String, Method> services = new HashMap<>();
        Method[] methods;

        //Uploading components
        if (c.isAnnotationPresent(RestController.class)) {
            methods = c.getDeclaredMethods();
            for (Method m : methods) {
                if (m.isAnnotationPresent(GetMapping.class)) {
                    String key = m.getAnnotation(GetMapping.class).value();
                    services.put(key, m);
                }
            }
        }

        //burned code for a example practice hello
        URL serviceUrl = new URL("http://localhost:8080/App/hello");
        String path = serviceUrl.getPath();
        System.out.println("PATH ------->" + path);

        String serviceName = path.substring(4);
        System.out.println("Service Name ------> " + serviceName);

        Method m = services.get(serviceName);
        System.out.println("hello world" + m.invoke(null));

        //pi
        URL serviceUrl1 = new URL("http://localhost:8080/App/Pi");
        String path1 = serviceUrl1.getPath();
        System.out.println("PATH ------->" + path1);

        String serviceName1 = path1.substring(4);
        System.out.println("Service Name ------> " + serviceName);

        Method m1 = services.get(serviceName1);
        System.out.println("pi" + m1.invoke(null));


        // funciones
        URL serviceUrl2 = new URL("http://localhost:8080/App/funciones");
        String path2 = serviceUrl2.getPath();
        System.out.println("PATH ------->" + path2);

        String serviceName2 = path.substring(4);
        System.out.println("Service Name ------> " + serviceName2);

        Method m2 = services.get(serviceName);
        System.out.println("funciones" + m2.invoke(null));


        // Estudiantes
        URL serviceUrl3 = new URL("http://localhost:8080/App/estudiantes");
        String path3 = serviceUrl3.getPath();
        System.out.println("PATH ------->" + path3);

        String serviceName3 = path.substring(4);
        System.out.println("Service Name ------> " + serviceName3);

        Method m3 = services.get(serviceName);
        System.out.println("estudiantes" + m3.invoke(null));


    }
}