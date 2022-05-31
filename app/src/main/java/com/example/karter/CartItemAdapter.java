package com.example.karter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.ViewHolder> {

    public interface ChangeQuantity{
        void onQuantityAdded(CartItem item);
        void onQuantityReduced(CartItem item);
    }

    public  interface  CartItemDelete{
        void onDeleteCartItemResult(CartItem item);
    }


    ArrayList<CartItem> allCartItems;
    Context context;

    public CartItemAdapter(Context context) {
        this.context = context;
    }

    public void setAllCartItems(ArrayList<CartItem> allCartItems) {
        this.allCartItems = allCartItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.cart_item_name.setText(allCartItems.get(position).getItem().getName());
        holder.price.setText("\u20B9"+allCartItems.get(position).getItem().getPrice());
        holder.total_price.setText("\u20B9"+allCartItems.get(position).getItem().getPrice()*allCartItems.get(position).getQuantity());
        holder.quantity.setText(allCartItems.get(position).getQuantity()+"");

        Glide.with(context)
                .asBitmap()
                .load(allCartItems.get(holder.getAdapterPosition()).getItem().getImageUrl())
                .into(holder.item_img);

        holder.cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setMessage("Are you sure You want to remove this item from your cart...")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                try{
                                    CartItemDelete cartItemDelete = (CartItemDelete) context;
                                    cartItemDelete.onDeleteCartItemResult(allCartItems.get(holder.getAdapterPosition()));
                                    notifyDataSetChanged();
                                }
                                catch (ClassCastException e){
                                    e.printStackTrace();
                                }
                            }
                        });

                builder.create().show();

            }
        });

        holder.plus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ChangeQuantity changeQuantity = (ChangeQuantity) context;
                    changeQuantity.onQuantityAdded(allCartItems.get(holder.getAdapterPosition()));
                    notifyDataSetChanged();
                }
                catch (ClassCastException e){
                    e.printStackTrace();
                }

            }
        });

        holder.minus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (allCartItems.get(holder.getAdapterPosition()).getQuantity()>1){
                    try {
                        ChangeQuantity changeQuantity = (ChangeQuantity) context;
                        changeQuantity.onQuantityReduced(allCartItems.get(holder.getAdapterPosition()));
                        notifyDataSetChanged();
                    }
                    catch (ClassCastException e){
                        e.printStackTrace();
                    }

                }
            }
        });




    }

    @Override
    public int getItemCount() {
        return allCartItems.size();
    }

    protected class  ViewHolder extends RecyclerView.ViewHolder{

        private ImageView item_img , cross , plus_btn , minus_btn;
        private  TextView cart_item_name , price , total_price , quantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            item_img=itemView.findViewById(R.id.cart_item_image);
            cart_item_name=itemView.findViewById(R.id.cart_item_name);
            price=itemView.findViewById(R.id.cart_item_price);
            total_price=itemView.findViewById(R.id.cart_item_total_price);

            quantity=itemView.findViewById(R.id.cart_item_quantity);

            cross = itemView.findViewById(R.id.cart_item_cross_icon);

            plus_btn=itemView.findViewById(R.id.cart_item_plus_btn);
            minus_btn=itemView.findViewById(R.id.cart_item_minus_btn);

        }
    }
}
