package edu.eci.arep.services;

import edu.eci.arep.utils.ShoppingListMemory;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * Service class for deleteProduct
 * @author Andrés Arias
 */
public class DeleteProductService implements RestService {

    private ShoppingListMemory listService;

    public DeleteProductService(ShoppingListMemory listService){
        this.listService = listService;
    }

    /**
     * Restservice's method requested
     * @param request Uri
     * @return response
     */
    @Override
    public String response(String request) {
        // Extraer el nombre del producto desde la URL
        String[] parts = request.split("\\?");
        if (parts.length > 1) {
            String query = parts[1];
            String[] queryParams = query.split("&");
            for (String param : queryParams) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2 && keyValue[0].equals("name")) {
                    String productName = keyValue[1];
                    // Decodificar el valor del nombre del producto
                    productName = URLDecoder.decode(productName, StandardCharsets.UTF_8);
                    // Eliminar el producto
                    listService.deleteProduct(productName);
                    return "{\"message\":\"Product deleted successfully.\"}";
                }
            }
        }
        return "{\"message\":\"Product not found.\"}";
    }
}
