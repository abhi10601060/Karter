package com.example.karter;

import android.os.Parcel;
import android.os.Parcelable;

public class CartItem  implements Parcelable {
    private GroceryItem item;
    private int quantity;

    public CartItem(GroceryItem item, int quantity)  {
        this.item = item;
        this.quantity = quantity;
    }

    protected CartItem(Parcel in) {
        item = in.readParcelable(GroceryItem.class.getClassLoader());
        quantity = in.readInt();
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

    public GroceryItem getItem() {
        return item;
    }

    public void setItem(GroceryItem item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(item, i);
        parcel.writeInt(quantity);
    }
}
