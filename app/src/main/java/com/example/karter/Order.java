package com.example.karter;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Order implements Parcelable {

    private ArrayList<CartItem> cartItems;
    private Address address;
    private String paymentMethod;
    private String date;


    public Order(ArrayList<CartItem> cartItems, Address address, String paymentMethod, String date) {
        this.cartItems = cartItems;
        this.address = address;
        this.paymentMethod = paymentMethod;
        this.date = date;
    }


    protected Order(Parcel in) {
        address = in.readParcelable(Address.class.getClassLoader());
        paymentMethod = in.readString();
        date = in.readString();
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

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(address, i);
        parcel.writeString(paymentMethod);
        parcel.writeString(date);
    }

}
