package com.example.karter;

import static com.example.karter.DetailsActivity.GROCERY_ITEM_KEY;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ReviewDialogue extends DialogFragment {

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseUser user = auth.getCurrentUser();

    private int rating = 0;

    public interface AddReview{
        void onAddReviewResult(Review review);
    }

    private  AddReview addReview;

    private EditText  review_text ;
    private Button btn_add;
    private TextView warning;
    private ImageView first_star , second_star, third_star, fourth_star, fifth_star;
    private RelativeLayout first_star_container, second_star_container, third_star_container, fourth_star_container, fifth_star_container;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.review_dialogue_layout,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view);

        initViews(view);

        Bundle bundle = getArguments();
        if (bundle!= null){

            GroceryItem item = bundle.getParcelable(GROCERY_ITEM_KEY);


            if(item!= null){
                getRating();

                btn_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String review = review_text.getText().toString();


                        if ( review.equals("") || rating==0){
                            warning.setText("Fill All Blanks!!!");
                            warning.setVisibility(View.VISIBLE);
                        }
                        else {
                            warning.setVisibility(View.GONE);
                            String date = getCurDate();

                            Review review1 = new Review(item.getId(),user.getUid(),user.getDisplayName(),review,rating,Calendar.getInstance().getTime());

                            try {
                                addReview = (AddReview) getActivity();

                                addReview.onAddReviewResult(review1);
                                dismiss();
                            }
                            catch (ClassCastException e){
                                e.printStackTrace();
                            }

                        }

                    }
                });

            }

        }

        return builder.create();
    }
    private void initViews(View view){

        review_text=view.findViewById(R.id.review_dialogue_edt_review);

        btn_add =view.findViewById(R.id.review_dialogue_btn_add);

        warning=view.findViewById(R.id.review_dialogue_txt_warning);

        first_star=view.findViewById(R.id.review_first_star);
        second_star=view.findViewById(R.id.review_second_star);
        third_star=view.findViewById(R.id.review_third_star);
        fourth_star=view.findViewById(R.id.review_fourth_star);
        fifth_star=view.findViewById(R.id.review_fifth_star);

        first_star_container=view.findViewById(R.id.review_dialogue_first_star_container);
        second_star_container=view.findViewById(R.id.review_dialogue_second_star_container);
        third_star_container=view.findViewById(R.id.review_dialogue_third_star_container);
        fourth_star_container=view.findViewById(R.id.review_dialogue_fourth_star_container);
        fifth_star_container=view.findViewById(R.id.review_dialogue_fifth_star_container);

    }

    private String getCurDate(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return simpleDateFormat.format(calendar.getTime());
    }
    private void getRating(){

        first_star_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                first_star.setVisibility(View.VISIBLE);
                second_star.setVisibility(View.GONE);
                third_star.setVisibility(View.GONE);
                fourth_star.setVisibility(View.GONE);
                fifth_star.setVisibility(View.GONE);
                rating=1;
            }
        });

        second_star_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                first_star.setVisibility(View.VISIBLE);
                second_star.setVisibility(View.VISIBLE);
                third_star.setVisibility(View.GONE);
                fourth_star.setVisibility(View.GONE);
                fifth_star.setVisibility(View.GONE);
                rating=2;
            }
        });
        third_star_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                first_star.setVisibility(View.VISIBLE);
                second_star.setVisibility(View.VISIBLE);
                third_star.setVisibility(View.VISIBLE);
                fourth_star.setVisibility(View.GONE);
                fifth_star.setVisibility(View.GONE);
                rating=3;
            }
        });
        fourth_star_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                first_star.setVisibility(View.VISIBLE);
                second_star.setVisibility(View.VISIBLE);
                third_star.setVisibility(View.VISIBLE);
                fourth_star.setVisibility(View.VISIBLE);
                fifth_star.setVisibility(View.GONE);
                rating=4;
            }
        });
        fifth_star_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                first_star.setVisibility(View.VISIBLE);
                second_star.setVisibility(View.VISIBLE);
                third_star.setVisibility(View.VISIBLE);
                fourth_star.setVisibility(View.VISIBLE);
                fifth_star.setVisibility(View.VISIBLE);
                rating=5;
            }
        });


    }
}
