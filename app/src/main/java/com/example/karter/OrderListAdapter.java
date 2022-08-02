package com.example.karter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {
    private ArrayList<CartItem> cart;
    private Context context;

    public OrderListAdapter(Context context) {
        this.context = context;
    }

    public void setCart(ArrayList<CartItem> cart) {
        this.cart = cart;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(cart.get(position).getItemName()+" "+"x" +cart.get(position).getQuantity() );
        holder.price.setText("\u20B9" + cart.get(position).getTotalPrice());

    }

    @Override
    public int getItemCount() {
        return cart.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name,price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.order_list_name);
            price = itemView.findViewById(R.id.order_list_price);
        }
    }
}
