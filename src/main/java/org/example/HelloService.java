package org.example;

@RestController
public class HelloService {

    @GetMapping("/hello")
    public static String hello(){
        return "Hello world";
    }

    @GetMapping("/Pi")
    public static double Pi(){
        return Math.PI;
    }

    @GetMapping("/funciones")
    public static  String[] funciones(){
        String[] funciones = {"coseno","seno","tangente"};
        return funciones;
    }

    @GetMapping("/estudiantes")
    public static String[] estudiantes(){
        String[] estudiantes = {"daniel","andres","sergio"};
        return estudiantes;
    }


}
