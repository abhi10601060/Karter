package com.example.karter;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.security.KeyRep;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Utils {
    private static final String DB_NAME = "fake_database";
    private static  final  String ALL_ITEMS = "all_items";
    private static final String ALL_CART_ITEMS= "all_CartItems";
    private static final String ALL_ADDRESSES= "all_Addresses";

    private static  final Gson gson = new Gson();
    private  static  final Type groceryType = new TypeToken<ArrayList<GroceryItem>>(){}.getType();
    private  static  final Type cartItemType = new TypeToken<ArrayList<CartItem>>(){}.getType();
    private  static  final Type addressType = new TypeToken<ArrayList<Address>>(){}.getType();

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

    public static  ArrayList<String> getAllCategories(){
        ArrayList<String> categoryItems = new ArrayList<>();
        categoryItems.add("Healthy");
        categoryItems.add("Pizza");
        categoryItems.add("Beverages");
        categoryItems.add("Fruits");
        categoryItems.add("Cleansers");

        return categoryItems;
    }

    public  static ArrayList<GroceryItem> getItemsByCategories(Context context , String category){

        ArrayList<GroceryItem> allItems = getAllItems(context);
        ArrayList<GroceryItem> ans = new ArrayList<>();
        for (GroceryItem i : allItems){
            if (i.getCategory().equalsIgnoreCase(category)){
                ans.add(i);
            }
        }
        if (ans.size()==0){
            return null;
        }
        return ans;

    }

    public static ArrayList<CartItem> getAllCartItems(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);
        ArrayList<CartItem> allCartItems = gson.fromJson(sharedPreferences.getString(ALL_CART_ITEMS,null),cartItemType);
        return allCartItems;
    }
    public static void addCartItem(Context context ,CartItem item){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);
        ArrayList<CartItem> allCartItems = gson.fromJson(sharedPreferences.getString(ALL_CART_ITEMS,null),cartItemType);

        if (allCartItems==null){
            ArrayList<CartItem> newCartItems = new ArrayList<>();
            newCartItems.add(item);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(ALL_CART_ITEMS, gson.toJson(newCartItems));
            editor.commit();
        }
        else {
            ArrayList<CartItem> newCartItems = new ArrayList<>();
            boolean found = false;
            for(CartItem i : allCartItems){
                if (item.getItem().getName().equals(i.getItem().getName())){
                    int newQuantity=i.getQuantity()+item.getQuantity();
                    i.setQuantity(newQuantity);
                    found=true;
                }
                newCartItems.add(i);
            }
            if (found==false){
                newCartItems.add(item);
            }
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(ALL_CART_ITEMS, gson.toJson(newCartItems));
            editor.commit();
        }
    }
    public static void removeCartItem(Context context , CartItem item){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);
        ArrayList<CartItem> allCartItems = gson.fromJson(sharedPreferences.getString(ALL_CART_ITEMS,null),cartItemType);

        ArrayList<CartItem> newCartItems = new ArrayList<>();
        for (CartItem i : allCartItems){
            if (!i.getItem().getName().equals(item.getItem().getName())){
                newCartItems.add(i);
            }
        }
        if (newCartItems.size()==0){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(ALL_CART_ITEMS,null);
            editor.commit();
        }
        else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(ALL_CART_ITEMS, gson.toJson(newCartItems));
            editor.commit();
        }

    }
    public static void addQuantity(Context context , CartItem item){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);
        ArrayList<CartItem> allCartItems= gson.fromJson(sharedPreferences.getString(ALL_CART_ITEMS,null),cartItemType);

        ArrayList<CartItem> newCartItems = new ArrayList<>();

        for (CartItem i : allCartItems){
            if (item.getItem().getName().equals(i.getItem().getName())){
                int newQuantity = i.getQuantity()+1;
                i.setQuantity(newQuantity);
            }
            newCartItems.add(i);
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.remove(ALL_CART_ITEMS);
        editor.putString(ALL_CART_ITEMS, gson.toJson(newCartItems));
        editor.commit();
    }
    public static void reduceQuantity(Context context , CartItem item){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);
        ArrayList<CartItem> allCartItems= gson.fromJson(sharedPreferences.getString(ALL_CART_ITEMS,null),cartItemType);

        ArrayList<CartItem> newCartItems = new ArrayList<>();

        for (CartItem i : allCartItems){
            if (item.getItem().getName().equals(i.getItem().getName())){
                int newQuantity = i.getQuantity()-1;
                i.setQuantity(newQuantity);
            }
            newCartItems.add(i);
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.remove(ALL_CART_ITEMS);
        editor.putString(ALL_CART_ITEMS, gson.toJson(newCartItems));
        editor.commit();
    }

    public static ArrayList<Address> getAllAddresses(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);
        ArrayList<Address> allAddresses = gson.fromJson(sharedPreferences.getString(ALL_ADDRESSES,null),addressType);
        return allAddresses;
    }
    public static void addAddress(Context context , Address address){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);
        ArrayList<Address> addresses = gson.fromJson(sharedPreferences.getString(ALL_ADDRESSES,null),addressType);

        if (addresses==null){
            ArrayList<Address> newAddresses = new ArrayList<>();
            newAddresses.add(address);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(ALL_ADDRESSES, gson.toJson(newAddresses));
            editor.commit();
        }
        else {
            addresses.add(address);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(ALL_ADDRESSES, gson.toJson(addresses));
            editor.commit();
        }
    }

    public static  void  removeAddresses(Context context, Address address){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);
        ArrayList<Address> addresses = gson.fromJson(sharedPreferences.getString(ALL_ADDRESSES,null),addressType);
        ArrayList<Address> newAddresses= new ArrayList<>();

        for (Address a : addresses){
            if (!address.getAddress().equals(a.getAddress())){
                newAddresses.add(a);
            }
        }
        if (newAddresses.size()==0){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(ALL_ADDRESSES,null);
            editor.commit();
        }
        else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(ALL_ADDRESSES, gson.toJson(newAddresses));
            editor.commit();
        }
    }

    public static void clearCart(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME , Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(ALL_CART_ITEMS);
        editor.commit();

    }

    public  static  void increasePopularity(Context context , GroceryItem item){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);

        ArrayList<GroceryItem> items = getAllItems(context);
        ArrayList<GroceryItem> newItems = new ArrayList<>();
        for(GroceryItem i : items){
            if (i.getName().equals(item.getName())){
                i.setPopularityPoint(i.getPopularityPoint()+1);
            }
            newItems.add(i);
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(ALL_ITEMS);
        editor.putString(ALL_ITEMS, gson.toJson(newItems));
        editor.commit();
    }


}
