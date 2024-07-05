package com.example.myapplication;

import com.example.myapplication.models.ProductDetail;

import java.util.ArrayList;
import java.util.List;

public class CartManager {

    private static CartManager instance;
    private List<ProductDetail> cartItems;

    private CartManager() {
        cartItems = new ArrayList<>();
    }

    public static synchronized CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void addToCart(ProductDetail product) {
        cartItems.add(product);
    }

    public void removeFromCart(ProductDetail product) {
        cartItems.remove(product);
    }

    public void removeFromCart(List<ProductDetail> products) {
        cartItems.removeAll(products);
    }

    public List<ProductDetail> getCartItems() {
        return cartItems;
    }

    public void clearCart() {
        cartItems.clear();
    }
}
