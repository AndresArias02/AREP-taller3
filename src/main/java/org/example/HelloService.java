package org.example;

@RestController
public class HelloService {

    @GetMapping("/hello")
    public static String hello(){
        return "Hello world";
    }
}
