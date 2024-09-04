package edu.eci.arep.controller;

import edu.eci.arep.Annotation.GetMapping;
import edu.eci.arep.Annotation.RequestMapping;
import edu.eci.arep.Annotation.RequestParam;
import edu.eci.arep.Annotation.RestController;
import edu.eci.arep.Enum.HttpMethod;
import edu.eci.arep.services.GetProductsService;

/**
 * AppController for requests
 * @author Andrés Arias
 */
@RestController
public class AppController {

    /**
     * Get Hello World
     * @return Hello world
     */
    @GetMapping(path = "/hello")
    public static String hello(){
        return "Hello world";
    }

    /**
     * Get Pi
     * @return Pi
     */
    @GetMapping(path = "/Pi")
    public static double Pi(){
        return Math.PI;
    }

    /**
     * Get Pi
     * @return Pi
     */
    @GetMapping(path = "/funciones")
    public static  String funciones(){
        String[] funciones = {"coseno","seno","tangente"};
        return String.join(", ", funciones);
    }

    /**
     * Get Pi
     * @return Pi
     */
    @GetMapping(path = "/estudiantes")
    public static String estudiantes(){
        String[] estudiantes = {"daniel","andres","sergio"};
        return String.join(", ", estudiantes);
    }

    /**
     * Get the welcome to the app
     * @param name name of the person
     * @return a welcome message
     */
    @GetMapping(path = "/Spring/helloName")
    public static String helloName(@RequestParam(value = "name", defaultValue = "Luis Daniel Benavides Navarro") String name){
        if (name == null || name.isEmpty()) {
            name = "Luis Daniel Benavides Navarro";
        }
        return "Hola " + name;
    }

    /**
     * Get the products in the shopping list with the description
     * @return The products
     */
    @RequestMapping(path = "/Spring/products", method = HttpMethod.GET)
    public static String products(){
        return GetProductsService.getAll();
    }
}