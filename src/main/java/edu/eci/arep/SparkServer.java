package edu.eci.arep;

import edu.eci.arep.services.RestService;

import java.util.HashMap;
import java.util.Map;

public class SparkServer {

    private static SparkServer instance;
    public static final Map<String, RestService> getServices = new HashMap();
    public static final Map<String, RestService> postServices = new HashMap();
    public static final Map<String, RestService> deleteServices = new HashMap();

    public static SparkServer getInstance(){
        if(instance == null){
            instance = new SparkServer();
        }
        return instance;
    }

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

    public static void get(String path, RestService service) {
        getServices.put(path, service);
    }

    public static void post(String path, RestService service) {
        postServices.put(path, service);
    }

    public static void delete(String path, RestService service) {
        deleteServices.put(path, service);
    }

    public static void staticFileLocation(String path) {
        HttpServer.getInstance().setStaticFileLocation(path);
    }

}

