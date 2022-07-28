package com.example.karter;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class GroceryItem implements Parcelable {
    private int id;
    private String name;
    private double price ;
    private String desc;
    private String imageUrl;
    private String category;
    private int available_amount;
    private int rate;
    private int popularityPoint;

    public GroceryItem() {
    }

    public GroceryItem(int id,String name, double price, String desc, String imageUrl, String category, int available_amount , int rate,int popularityPoint) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.desc = desc;
        this.imageUrl = imageUrl;
        this.category = category;
        this.available_amount = available_amount;
        this.rate=rate;
        this.popularityPoint=popularityPoint;
    }

    protected GroceryItem(Parcel in) {
        id = in.readInt();
        name = in.readString();
        price = in.readDouble();
        desc = in.readString();
        imageUrl = in.readString();
        category = in.readString();
        available_amount = in.readInt();
        rate = in.readInt();
        popularityPoint = in.readInt();
    }

    public static final Creator<GroceryItem> CREATOR = new Creator<GroceryItem>() {
        @Override
        public GroceryItem createFromParcel(Parcel in) {
            return new GroceryItem(in);
        }

        @Override
        public GroceryItem[] newArray(int size) {
            return new GroceryItem[size];
        }
    };

    @Override
    public String toString() {
        return "GroceryItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", desc='" + desc + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", category='" + category + '\'' +
                ", available_amount=" + available_amount +
                ", rate=" + rate +
                ", popularityPoint=" + popularityPoint +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getAvailable_amount() {
        return available_amount;
    }

    public void setAvailable_amount(int available_amount) {
        this.available_amount = available_amount;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getPopularityPoint() {
        return popularityPoint;
    }

    public void setPopularityPoint(int popularityPoint) {
        this.popularityPoint = popularityPoint;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeDouble(price);
        parcel.writeString(desc);
        parcel.writeString(imageUrl);
        parcel.writeString(category);
        parcel.writeInt(available_amount);
        parcel.writeInt(rate);
        parcel.writeInt(popularityPoint);
    }
}
