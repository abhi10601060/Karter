package com.example.karter;

import android.os.Parcel;
import android.os.Parcelable;

public class CartItem   {

    private String cartItemId;
    private int quantity;
    private double singleItemPrice;
    private double totalPrice ;
    private String itemName;
    private String imageUrl;

    public CartItem(String cartItemId, int quantity, double singleItemPrice, double totalPrice, String itemName, String imageUrl) {
        this.cartItemId = cartItemId;
        this.quantity = quantity;
        this.singleItemPrice = singleItemPrice;
        this.totalPrice = totalPrice;
        this.itemName = itemName;
        this.imageUrl = imageUrl;
    }

    public CartItem() {
    }

    public String getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(String cartItemId) {
        this.cartItemId = cartItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSingleItemPrice() {
        return singleItemPrice;
    }

    public void setSingleItemPrice(double singleItemPrice) {
        this.singleItemPrice = singleItemPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
