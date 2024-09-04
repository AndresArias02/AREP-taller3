package edu.eci.arep.utils;

import java.util.*;

public class ShoppingListMemory {
    private Map<String, String> products = new HashMap<>();

    public void addProduct(String name, String description) {
        products.put(name, description);
    }

    public List<String> getProducts() {
        Set<String> Keys = products.keySet();
        List<String> nameProducts = new ArrayList<>();
        for(String k: Keys ) {
            nameProducts.add(k);
        }
        return nameProducts;
    }

    public void deleteProduct(String name) {
        products.remove(name);
    }

    public Map<String,String> getAllProducts(){
        return products;
    }
}
