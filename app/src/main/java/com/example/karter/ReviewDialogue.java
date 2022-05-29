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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ReviewDialogue extends DialogFragment {

    public interface AddReview{
        void onAddReviewResult(Review review);
    }

    private  AddReview addReview;

    private EditText review_name , review_text ;
    private Button btn_add;
    private TextView warning;

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
                String name = String.valueOf(review_name.getText());
                String review = review_text.getText().toString();

                btn_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (name=="" || review==""){
                            warning.setText("Fill All Blanks!!!");
                            warning.setVisibility(View.VISIBLE);
                        }
                        else {
                            warning.setVisibility(View.GONE);
                            String date = getCurDate();

                            Review review1 = new Review(item.getId(),name,review,date);
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
        review_name = view.findViewById(R.id.review_dialogue_edt_name);
        review_text=view.findViewById(R.id.review_dialogue_edt_review);

        btn_add =view.findViewById(R.id.review_dialogue_btn_add);

        warning=view.findViewById(R.id.review_dialogue_txt_warning);
    }

    private String getCurDate(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return simpleDateFormat.format(calendar.getTime());
    }
}
