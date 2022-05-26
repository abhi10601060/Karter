package com.example.karter;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.security.KeyRep;
import java.util.ArrayList;

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

}
