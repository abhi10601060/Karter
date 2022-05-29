package com.example.karter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReviewAdapter  extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    public interface  RemoveReview{
        void onRemoveResult(Review review);
    }
    RemoveReview removeReview;

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

        holder.parent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setMessage("Do you want to delete this review...")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                try {
                                    removeReview = (RemoveReview) context;
                                    removeReview.onRemoveResult(allReviews.get(holder.getAdapterPosition()));
                                    notifyDataSetChanged();
                                }
                                catch (ClassCastException e){
                                    e.printStackTrace();
                                }
                            }
                        });

                builder.create().show();
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return allReviews.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder{

        private TextView name, date, text;
        private RelativeLayout parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.review_name);
            text = itemView.findViewById(R.id.review_description);
            date= itemView.findViewById(R.id.review_date);
            parent = itemView.findViewById(R.id.review_RL);

        }
    }
}
