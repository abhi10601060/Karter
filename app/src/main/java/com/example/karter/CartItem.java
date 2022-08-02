package com.example.karter;

import android.os.Parcel;
import android.os.Parcelable;

public class CartItem implements Parcelable  {

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

    protected CartItem(Parcel in) {
        cartItemId = in.readString();
        quantity = in.readInt();
        singleItemPrice = in.readDouble();
        totalPrice = in.readDouble();
        itemName = in.readString();
        imageUrl = in.readString();
    }

    public static final Creator<CartItem> CREATOR = new Creator<CartItem>() {
        @Override
        public CartItem createFromParcel(Parcel in) {
            return new CartItem(in);
        }

        @Override
        public CartItem[] newArray(int size) {
            return new CartItem[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(cartItemId);
        parcel.writeInt(quantity);
        parcel.writeDouble(singleItemPrice);
        parcel.writeDouble(totalPrice);
        parcel.writeString(itemName);
        parcel.writeString(imageUrl);
    }
}
