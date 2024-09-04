package edu.eci.arep.utils;

import java.util.*;

/**
 * class for saving the products
 */
public class ShoppingListMemory {
    private Map<String, String> products = new HashMap<>();

    /**
     * method to add a product
     * @param name product name
     * @param description description product
     */
    public void addProduct(String name, String description) {
        products.put(name, description);
    }

    /**
     * method to get al products names
     * @return products names
     */
    public List<String> getProducts() {
        Set<String> Keys = products.keySet();
        List<String> nameProducts = new ArrayList<>();
        for(String k: Keys ) {
            nameProducts.add(k);
        }
        return nameProducts;
    }

    /**
     * method to delete a product
     * @param name of the product
     */
    public void deleteProduct(String name) {
        products.remove(name);
    }

    /**
     * method to get the products with name and descrption
     * @return map with products
     */
    public Map<String,String> getAllProducts(){
        return products;
    }
}
