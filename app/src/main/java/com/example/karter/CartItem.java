package com.example.karter;

public class CartItem {
    private GroceryItem item;
    private int quantity;

    public CartItem(GroceryItem item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public GroceryItem getItem() {
        return item;
    }

    public void setItem(GroceryItem item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
