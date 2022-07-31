package com.example.karter;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;

public class Order implements Parcelable {

    private String orderId;
    private ArrayList<CartItem> cartItems;
    private Address address;
    private String description;
    private double totalAmount;
    private String paymentMethod;
    private Date date;

    public Order(ArrayList<CartItem> cartItems, Address address, String description, double totalAmount, String paymentMethod, Date date) {
        this.orderId = "";
        this.cartItems = cartItems;
        this.address = address;
        this.description = description;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.date = date;
    }

    public Order() {
    }

    protected Order(Parcel in) {
        orderId = in.readString();
        address = in.readParcelable(Address.class.getClassLoader());
        description = in.readString();
        totalAmount = in.readDouble();
        paymentMethod = in.readString();
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public ArrayList<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(ArrayList<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(orderId);
        parcel.writeParcelable(address, i);
        parcel.writeString(description);
        parcel.writeDouble(totalAmount);
        parcel.writeString(paymentMethod);
    }
}
