package com.example.karter;

import androidx.annotation.Nullable;

import java.util.Date;

public class Review {
    private int itemId;
    private String UserId;
    private String name;
    private String text;
    private int rating;
    private Date date;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Review() {
    }

    public Review(int itemId, String userId, String name, String text, int rating, Date date) {
        this.itemId = itemId;
        UserId = userId;
        this.name = name;
        this.text = text;
        this.rating = rating;
        this.date = date;
    }
}
