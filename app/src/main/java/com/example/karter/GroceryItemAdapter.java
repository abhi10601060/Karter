package com.example.karter;

import static com.example.karter.DetailsActivity.GROCERY_ITEM_KEY;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class GroceryItemAdapter extends RecyclerView.Adapter<GroceryItemAdapter.ViewHolder> {

    private ArrayList<GroceryItem> groceryItems ;
    private  Context context;

    public GroceryItemAdapter(Context context) {
        this.context = context;
    }

    public void setGroceryItems(ArrayList<GroceryItem> groceryItems) {
        this.groceryItems = groceryItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lgrocery_item_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.itemName.setText(groceryItems.get(position).getName());
        holder.price.setText(""+groceryItems.get(position).getPrice());

        Glide.with(context)
                .asBitmap()
                .load(groceryItems.get(position).getImageUrl())
                .into(holder.itemImage);

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , DetailsActivity.class);
                intent.putExtra(GROCERY_ITEM_KEY,groceryItems.get(holder.getAdapterPosition()));
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return groceryItems.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder{
        private RelativeLayout parent;
        private TextView itemName;
        private ImageView itemImage;
        private TextView price;
        private TextView btn_add;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.parent_Rl);
            itemName= itemView.findViewById(R.id.grocery_item_name_txt);
            itemImage = itemView.findViewById(R.id.grocery_item_image);
            price = itemView.findViewById(R.id.price);
            btn_add=itemView.findViewById(R.id.btn_add);
        }
    }
}
