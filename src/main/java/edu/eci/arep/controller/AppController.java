package edu.eci.arep.controller;

import edu.eci.arep.Annotation.GetMapping;
import edu.eci.arep.Annotation.RequestMapping;
import edu.eci.arep.Annotation.RequestParam;
import edu.eci.arep.Annotation.RestController;
import edu.eci.arep.Enum.HttpMethod;
import edu.eci.arep.services.AddProductService;
import edu.eci.arep.services.GetProductsService;

@RestController
public class AppController {

    @GetMapping(path = "/hello")
    public static String hello(){
        return "Hello world";
    }

    @GetMapping(path = "/Pi")
    public static double Pi(){
        return Math.PI;
    }

    @GetMapping(path = "/funciones")
    public static  String funciones(){
        String[] funciones = {"coseno","seno","tangente"};
        return String.join(", ", funciones);
    }

    @GetMapping(path = "/estudiantes")
    public static String estudiantes(){
        String[] estudiantes = {"daniel","andres","sergio"};
        return String.join(", ", estudiantes);
    }

    @GetMapping(path = "/Spring/helloName")
    public static String helloName(@RequestParam(value = "name", defaultValue = "Daniel Benavidez") String name){
        return "Hello" + name;
    }

    @RequestMapping(path = "/Spring/products", method = HttpMethod.GET)
    public static String products(){
        return GetProductsService.getAll();
    }
}
