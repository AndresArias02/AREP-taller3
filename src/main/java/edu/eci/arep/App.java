package edu.eci.arep;

/**
 * Shopping class to start the aplication
 * @author Andres Felipe Arias
 * @author Luis Daniel Benavides Navarro
 */

import edu.eci.arep.Annotation.ComponentScan;
import edu.eci.arep.servers.HttpServer;
import edu.eci.arep.servers.SparkServer;
import edu.eci.arep.servers.SpringServer;
import edu.eci.arep.services.AddProductService;
import edu.eci.arep.services.DeleteProductService;
import edu.eci.arep.services.GetProductsService;
import edu.eci.arep.utils.ShoppingListMemory;

import java.io.IOException;

/**
 *
 */
@ComponentScan(basePackage = "edu.eci.arep")
public class App {

    private static final ShoppingListMemory shoppingListMemory = new ShoppingListMemory();

    /**
     * main class
     * @param args args
     * @throws IOException if a IO error occurs
     */
    public static void main(String[] args) throws IOException {
        // Set the static files location
        SparkServer.staticFileLocation("target/classes/webroot");

        // sparkService
        loadSparkFramework();

        // springService
        loadSpringService();

        // Start the servers
        try {
            if (!HttpServer.getInstance().isRunning())
                HttpServer.getInstance().start();
        } catch (Exception e) {
            System.err.println("Error en el servidor");
            System.exit(1);
        }

    }

    /**
     * Method to load sparkFramework
     */
    private static void loadSparkFramework(){
        SparkServer.get("/app/getProducts", (req) ->{
            GetProductsService getProductsService =  new GetProductsService(shoppingListMemory);
            String response = getProductsService.response(req);
            return response;
        });

        SparkServer.post("/app/addProduct", (req) ->{
            AddProductService addProductService = new AddProductService(shoppingListMemory);
            String response = addProductService.response(req);
            return response;
        });

        SparkServer.delete("/app/deleteProduct", (req) ->{
            DeleteProductService deleteProductService = new DeleteProductService(shoppingListMemory);
            String response = deleteProductService.response(req);
            return response;
        });
    }

    /**
     * Method to load SpringServer
     */
    private static void loadSpringService(){
        SpringServer.getInstance();
    }

}

