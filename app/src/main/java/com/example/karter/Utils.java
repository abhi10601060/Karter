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
        if(allItems==null){
            initAllItems(context);
        }
    }
    public  static  void  initAllItems(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);

        ArrayList<GroceryItem> allItems = new ArrayList<>();
        allItems.add(new GroceryItem("Milk" , 55,"Milk is Good for health","https://i.pinimg.com/originals/08/7b/ce/087bce790c5e54d0ba70d608f1e09535.jpg",
               "Healthy" , 10 ));

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
