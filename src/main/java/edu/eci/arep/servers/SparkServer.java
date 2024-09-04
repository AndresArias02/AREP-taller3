package edu.eci.arep.servers;

import edu.eci.arep.services.RestService;
import java.util.HashMap;
import java.util.Map;

/**
 * SparkServer Class
 * @author Andrés Arias
 */
public class SparkServer {

    private static SparkServer instance;
    public static final Map<String, RestService> getServices = new HashMap();
    public static final Map<String, RestService> postServices = new HashMap();
    public static final Map<String, RestService> deleteServices = new HashMap();

    /**
     * Method to get the instance of the SparkServer class.
     * @return The instance of the SparkServer class.
     */
    public static SparkServer getInstance(){
        if(instance == null){
            instance = new SparkServer();
        }
        return instance;
    }

    /**
     * Method to find the handler of a route.
     *
     * @param method The method of the route.
     * @param path   The path of the route.
     * @return The RestService of the route.
     */
    public static RestService findHandler(String path, String method) {
        if ("GET".equalsIgnoreCase(method)) {
            return getInstance().getServices.get(path);
        } else if ("POST".equalsIgnoreCase(method)) {
            return getInstance().postServices.get(path);
        } else if ("DELETE".equalsIgnoreCase(method)){
            return getInstance().deleteServices.get(path);
        }else {
            return null;
        }
    }


    /**
     * Method to define a GET route.
     *
     * @param path    The path of the route.
     * @param service The RestService.
     */
    public static void get(String path, RestService service) {
        getServices.put(path, service);
    }


    /**
     * Method to define a POST route.
     *
     * @param path    The path of the route.
     * @param service The RestService.
     */
    public static void post(String path, RestService service) {
        postServices.put(path, service);
    }


    /**
     * Method to define a DELETE route.
     *
     * @param path    The path of the route.
     * @param service The RestService.
     */
    public static void delete(String path, RestService service) {
        deleteServices.put(path, service);
    }

    /**
     * Method to set the static file location.
     * @param path The path of the static file location.
     */
    public static void staticFileLocation(String path) {
        HttpServer.getInstance().setStaticFileLocation(path);
    }

}

