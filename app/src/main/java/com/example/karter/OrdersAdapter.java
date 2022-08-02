package com.example.karter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {
    private ArrayList<Order> allOrders;
    private Context context;

    public OrdersAdapter(Context context) {
        this.context = context;
    }

    public void setAllOrders(ArrayList<Order> allOrders) {
        this.allOrders = allOrders;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.description.setText("Description : "+ allOrders.get(position).getDescription());
        Date date = allOrders.get(position).getDate();
        SimpleDateFormat sdt_day = new SimpleDateFormat("MMM d, yyyy | HH:mm");
        holder.date.setText(sdt_day.format(date));

        ArrayList<CartItem> cart = allOrders.get(position).getCartItems();
        if (cart.size()>5){
            holder.plusCount.setText("+"+ (cart.size()-5));
        }
        if(cart.size()>0){
            Glide.with(context)
                    .asBitmap()
                    .load(cart.get(0).getImageUrl())
                    .into(holder.img1);
        }
        if(cart.size()>1){
            Glide.with(context)
                    .asBitmap()
                    .load(cart.get(1).getImageUrl())
                    .into(holder.img2);
        }
        if(cart.size()>2){
            Glide.with(context)
                    .asBitmap()
                    .load(cart.get(2).getImageUrl())
                    .into(holder.img3);
        }
        if(cart.size()>3){
            Glide.with(context)
                    .asBitmap()
                    .load(cart.get(3).getImageUrl())
                    .into(holder.img4);
        }
        if(cart.size()>4){
            Glide.with(context)
                    .asBitmap()
                    .load(cart.get(4).getImageUrl())
                    .into(holder.img5);
        }

        holder.itemCount.setText(cart.size() + " items");
        holder.totalPrice.setText("\u20B9 " + allOrders.get(holder.getAdapterPosition()).getTotalAmount());
        holder.payment_method.setText(allOrders.get(position).getPaymentMethod());

    }

    @Override
    public int getItemCount() {
        return allOrders.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder{
        private TextView description , date, payment_method , itemCount , totalPrice , plusCount;
        private ImageView img1 ,img2,img3,img4,img5;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            description = itemView.findViewById(R.id.desc_text);
            date=itemView.findViewById(R.id.orders_txt_date);
            payment_method =itemView.findViewById(R.id.orders_txt_payment);
            itemCount = itemView.findViewById(R.id.orders_item_count);
            totalPrice=itemView.findViewById(R.id.orders_total_price);
            plusCount=itemView.findViewById(R.id.orders_rem_count_txt);

            img1= itemView.findViewById(R.id.orders_img_1);
            img2= itemView.findViewById(R.id.orders_img_2);
            img3= itemView.findViewById(R.id.orders_img_3);
            img4= itemView.findViewById(R.id.orders_img_4);
            img5= itemView.findViewById(R.id.orders_img_5);


        }
    }

}
