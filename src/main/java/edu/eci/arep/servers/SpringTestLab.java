package edu.eci.arep.servers;

import edu.eci.arep.Annotation.GetMapping;
import edu.eci.arep.Annotation.RestController;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * SpringTest lab
 * @author Andrés Arias
 */
public class SpringTestLab {
    /**
     * method to invoke all method with an annotation requested
     * @param args args
     * @throws ClassNotFoundException if a CNF error occurs
     * @throws MalformedURLException if a MFU error occurs
     * @throws InvocationTargetException if an IT error occurs
     * @throws IllegalAccessException if a IA error occurs
     */
    public static void test(String[] args) throws ClassNotFoundException, MalformedURLException, InvocationTargetException, IllegalAccessException {
        Class c = Class.forName(args[0]);
        Map<String, Method> services = new HashMap<>();
        Method[] methods;
        Parameter[] parameters;

        //Uploading components
        if (c.isAnnotationPresent(RestController.class)) {
            methods = c.getDeclaredMethods();
            for (Method m : methods) {
                if (m.isAnnotationPresent(GetMapping.class)) {
                    String key = m.getAnnotation(GetMapping.class).path();
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
        System.out.println(m.invoke(null));
        System.out.println("------------------------------------------------------------");

        //pi
        URL serviceUrl1 = new URL("http://localhost:8080/App/Pi");
        String path1 = serviceUrl1.getPath();
        System.out.println("PATH ------->" + path1);

        String serviceName1 = path1.substring(4);
        System.out.println("Service Name ------> " + serviceName1);

        Method m1 = services.get(serviceName1);
        System.out.println(m1.invoke(null));
        System.out.println("------------------------------------------------------------");


        // funciones
        URL serviceUrl2 = new URL("http://localhost:8080/App/funciones");
        String path2 = serviceUrl2.getPath();
        System.out.println("PATH ------->" + path2);

        String serviceName2 = path2.substring(4);
        System.out.println("Service Name ------> " + serviceName2);

        Method m2 = services.get(serviceName2);
        System.out.println(m2.invoke(null));
        System.out.println("------------------------------------------------------------");


        // Estudiantes
        URL serviceUrl3 = new URL("http://localhost:8080/App/estudiantes");
        String path3 = serviceUrl3.getPath();
        System.out.println("PATH ------->" + path3);

        String serviceName3 = path3.substring(4);
        System.out.println("Service Name ------> " + serviceName3);

        Method m3 = services.get(serviceName3);
        System.out.println(m3.invoke(null));
        System.out.println("------------------------------------------------------------");


    }
}