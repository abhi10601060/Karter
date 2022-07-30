package com.example.karter;

import android.os.Parcel;
import android.os.Parcelable;

public class CartItem   {

    private String cartItemId;
    private int quantity;
    private double singleItemPrice;
    private double totalPrice ;

    public CartItem() {
    }

    public CartItem(String cartItemId, int quantity, double singleItemPrice, double totalPrice) {
        this.cartItemId = cartItemId;
        this.quantity = quantity;
        this.singleItemPrice = singleItemPrice;
        this.totalPrice = totalPrice;
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
}
