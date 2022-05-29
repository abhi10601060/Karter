package com.example.karter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReviewAdapter  extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private ArrayList<Review> allReviews;
    Context context;

    public ReviewAdapter(Context context) {
        this.context = context;
    }

    public void setAllReviews(ArrayList<Review> allReviews) {
        this.allReviews = allReviews;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(allReviews.get(position).getName());
        holder.text.setText(allReviews.get(position).getText());
        holder.date.setText(allReviews.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return allReviews.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder{

        private TextView name, date, text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.review_name);
            text = itemView.findViewById(R.id.review_description);
            date= itemView.findViewById(R.id.review_date);

        }
    }
}
