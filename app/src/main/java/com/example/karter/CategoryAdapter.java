package com.example.karter;

import android.content.ComponentName;
import android.content.Context;
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

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private ArrayList<CategoryItem> allCategories;
    private Context context ;

    public void setAllCategories(ArrayList<CategoryItem> allCategories) {
        this.allCategories = allCategories;
    }

    public CategoryAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.category_name.setText(allCategories.get(position).getName());
        switch (position){
            case 0 :
                holder.mainLayout.setBackground(context.getDrawable(R.drawable.cat1_background));
                Glide.with(context)
                        .asBitmap()
                        .load(R.drawable.heal_img)
                        .into(holder.clipart);

                break;
            case 1 :
                holder.mainLayout.setBackground(context.getDrawable(R.drawable.cat2_background));
                Glide.with(context)
                        .asBitmap()
                        .load(R.drawable.pizza_img)
                        .into(holder.clipart);

                break;
            case 2 :
                holder.mainLayout.setBackground(context.getDrawable(R.drawable.cat3_background));
                Glide.with(context)
                        .asBitmap()
                        .load(R.drawable.beverages_img)
                        .into(holder.clipart);

                break;
            case 3 :
                holder.mainLayout.setBackground(context.getDrawable(R.drawable.cat4_background));
                Glide.with(context)
                        .asBitmap()
                        .load(R.drawable.fruits_img)
                        .into(holder.clipart);

                break;
            case 4 :
                holder.mainLayout.setBackground(context.getDrawable(R.drawable.cat5_background));
                Glide.with(context)
                        .asBitmap()
                        .load(R.drawable.cleansar_img)
                        .into(holder.clipart);
        }
    }

    @Override
    public int getItemCount() {
        return allCategories.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout mainLayout;
        private ImageView clipart;
        private TextView category_name;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mainLayout=itemView.findViewById(R.id.category_rl);
            clipart = itemView.findViewById(R.id.category_clipart);
            category_name=itemView.findViewById(R.id.category_text);
        }
    }
}
