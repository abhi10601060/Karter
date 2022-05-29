package com.example.karter;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.security.KeyRep;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Utils {
    private static final String DB_NAME = "fake_database";
    private static  final  String ALL_ITEMS = "all_items";
    private static  final Gson gson = new Gson();
    private  static  final Type groceryType = new TypeToken<ArrayList<GroceryItem>>(){}.getType();
    private  static  int Id=0;


    public  static  void initSharedPreferences(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);
        ArrayList<GroceryItem> allItems = gson.fromJson(sharedPreferences.getString(ALL_ITEMS,null),groceryType) ;

//        initAllItems(context);

        if(allItems==null){
            initAllItems(context);
        }
    }
    public  static  void  initAllItems(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);

        ArrayList<GroceryItem> allItems = new ArrayList<>();
        allItems.add(new GroceryItem("Milk" , 55,"Milk is Good for health","https://i.pinimg.com/originals/08/7b/ce/087bce790c5e54d0ba70d608f1e09535.jpg",
                "Healthy" , 10 ));

        allItems.add(new GroceryItem("Diet coke" , 40,"Diet Coke is  good for Health","https://previews.123rf.com/images/denismart/denismart1703/denismart170300169/73987424-london-uk-march-21-2017-a-can-of-coca-cola-diet-drink-on-white-background-the-drink-is-produced-and-.jpg",
                "Beverages",100));

        allItems.add(new GroceryItem("Oranges" , 100 , "Oranges are full of vitamin C","https://previews.123rf.com/images/atoss/atoss1501/atoss150100034/35300340-orange-tranches-de-fruits-isol%C3%A9-sur-fond-blanc.jpg",
                "Fruits",25));

        allItems.add((new GroceryItem("Pizza", 200, "Pizza is a Junk fruit" , "https://thumbs.dreamstime.com/b/tasty-pepperoni-pizza-top-view-hot-flat-lay-isolated-white-background-138316432.jpg",
                "Junk" , 15)));

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ALL_ITEMS,gson.toJson(allItems));
        editor.commit();

    }

    public static ArrayList<GroceryItem> getAllItems(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);
        ArrayList<GroceryItem> allItems= gson.fromJson(sharedPreferences.getString(ALL_ITEMS,null),groceryType);
        return allItems;

    }

     public static  int getId(){
        Id++;
        return  Id;
    }

    public static void changeRate(Context context , int Id , int newRate){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        ArrayList<GroceryItem> allItems = gson.fromJson(sharedPreferences.getString(ALL_ITEMS,null),groceryType);

        if(allItems!= null){
            ArrayList<GroceryItem> newItems = new ArrayList<>();
            for (GroceryItem i : allItems){
                if (i.getId()==Id){
                    i.setRate(newRate);
                    newItems.add(i);
                }
                else {
                    newItems.add(i);
                }
            }
            editor.remove(ALL_ITEMS);
            editor.putString(ALL_ITEMS,gson.toJson(newItems));
            editor.commit();
        }

    }

    public static void  addReview(Context context , Review review , int itemId){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);
        ArrayList<GroceryItem> allItems = getAllItems(context);

        if (allItems!=null){

            ArrayList<GroceryItem> newItems = new ArrayList<>();
            for(GroceryItem i : allItems){
                if (i.getId()==itemId){
                    ArrayList<Review> reviews = i.getReviews();
                    reviews.add(review);
                    i.setReviews(reviews);
                }
                newItems.add(i);
            }
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(ALL_ITEMS);
            editor.putString(ALL_ITEMS,gson.toJson(newItems));
            editor.commit();
        }
    }

    public static ArrayList<Review> getReviews(Context context , int itemId){

        ArrayList<GroceryItem> allItems = getAllItems(context);

        for (GroceryItem i : allItems){
            if(itemId==i.getId()){
                ArrayList<Review> reviews = i.getReviews();
                return reviews;
            }
        }
        return null;
    }
    public  static  void removeReview(Context context , int itemId , Review review){
        ArrayList<GroceryItem> allItems = getAllItems(context);
        ArrayList<GroceryItem> newItems=new ArrayList<>();
        for (GroceryItem i : allItems){
            if (itemId==i.getId()){
                ArrayList<Review> reviews = i.getReviews();
                ArrayList<Review> newReview = new ArrayList<>();
                for (Review r : reviews){
                    if (!r.equals(review)){
                        newReview.add(r);
                    }
                }
                i.setReviews(newReview);
            }
            newItems.add(i);
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(ALL_ITEMS);
        editor.putString(ALL_ITEMS, gson.toJson(newItems));
        editor.commit();

    }
    public static ArrayList<GroceryItem> searchByName(Context context , String name){
        ArrayList<GroceryItem> allItems = getAllItems(context);
        if (allItems!= null){
            Set<GroceryItem> items = new HashSet<>();
            for (GroceryItem i : allItems){
                if (i.getName().equalsIgnoreCase(name)){
                    items.add(i);
                }

                String[] names = i.getName().split(" ");
                for (String s : names){
                    if (s.equalsIgnoreCase(name)){
                        items.add(i);
                    }
                }
            }
            ArrayList<GroceryItem> result = new ArrayList<>();
            for (GroceryItem g : items){
                result.add(g);
            }
            return result;
        }
        return null;


    }

}
