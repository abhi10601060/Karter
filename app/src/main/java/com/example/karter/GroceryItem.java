package com.example.karter;

import java.util.ArrayList;

public class GroceryItem {
    private int id;
    private String name;
    private double price ;
    private String desc;
    private String imageUrl;
    private String category;
    private int available_amount;
    private int rate;
    private int popularityPoint;
    private ArrayList<Review> reviews;

    public GroceryItem( String name, double price, String desc, String imageUrl, String category, int available_amount) {
        this.id = Utils.getId();
        this.name = name;
        this.price = price;
        this.desc = desc;
        this.imageUrl = imageUrl;
        this.category = category;
        this.available_amount = available_amount;
        this.rate=0;
        this.popularityPoint=0;
        reviews= new ArrayList<>();
    }

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
                ", reviews=" + reviews +
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

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }
}
