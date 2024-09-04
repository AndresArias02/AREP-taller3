package edu.eci.arep.services;

import edu.eci.arep.utils.ShoppingListMemory;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class AddProductService implements RestService {

    private ShoppingListMemory cartService;

    public AddProductService(ShoppingListMemory cartService) {
        this.cartService = cartService;
    }

    @Override
    public String response(String request) {
        try {
            String[] parts = request.split("\\?");
            if (parts.length > 1) {
                String query = parts[1];
                String[] queryParams = query.split("&");
                String name = null;
                String description = null;

                for (String param : queryParams) {
                    String[] keyValue = param.split("=");
                    if (keyValue.length == 2) {
                        if (keyValue[0].equals("name")) {
                            name = URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8);
                        } else if (keyValue[0].equals("descr")) {
                            description = URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8);
                        }
                    }
                }

                if (name != null && description != null) {
                    if (cartService.getProducts().contains(name)) {
                        return "{\"message\":\"Product already exists in the cart.\"}";
                    } else {
                        cartService.addProduct(name, description);
                        return "{\"message\":\"Product added successfully.\"}";
                    }
                }
            }
            return "{\"message\":\"Invalid request.\"}";
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"message\":\"Error processing request.\"}";
        }
    }
}
