package edu.eci.arep.services;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import edu.eci.arep.utils.ShoppingListMemory;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service class for getProducts
 * @author Andrés Arias
 */
public class GetProductsService implements RestService {

    public static ShoppingListMemory cartService;

    /**
     * constructor of the service
     * @param cartService shoppingListMemory
     */
    public GetProductsService(ShoppingListMemory cartService) {
        this.cartService = cartService;
    }

    /**
     * Restservice's method requested
     * @param request Uri
     * @return response
     */
    @Override
    public String response(String request) {
        List<String> products = cartService.getProducts();

        // Convertir la lista de productos en una cadena JSON válida
        String jsonProducts = products.stream()
                .map(product -> "\"" + product + "\"")
                .collect(Collectors.joining(","));
        return "{\"products\":[" + jsonProducts + "]}";
    }

    /**
     * Method to get all the products with the name and description
     * @return response
     */
    public static String getAll(){
        Map<String,String> products = cartService.getAllProducts();
        Gson gson = new Gson();
        JsonArray jsonArray = new JsonArray();

        for (Map.Entry<String, String> entry : products.entrySet()) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("name", entry.getKey());
            jsonObject.addProperty("description", entry.getValue());
            jsonArray.add(jsonObject);
        }
        return gson.toJson(jsonArray);
    }
}
