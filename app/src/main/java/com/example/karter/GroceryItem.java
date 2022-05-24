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

    public GroceryItem(int id, String name, double price, String desc, String imageUrl, String category, int available_amount) {
        this.id = id;
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





}
