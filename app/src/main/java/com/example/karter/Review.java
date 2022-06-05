package com.example.karter;

import androidx.annotation.Nullable;

public class Review {
    private int itemId;
    private String name;
    private String text;
    private  String date;
    private int rating;

    public int getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }



    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Review{" +
                "itemId=" + itemId +
                ", name='" + name + '\'' +
                ", text='" + text + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Review(int itemId, String name, String text, String date , int rating) {
        this.itemId = itemId;
        this.name = name;
        this.text = text;
        this.date = date;
        this.rating=rating;
    }


    public boolean equals(Review r) {
        if (this.name.equals(r.getName()) && this.text.equals(r.getText()) && this.date.equals(r.getDate())){
            return true;
        }
        return false;
    }
}
