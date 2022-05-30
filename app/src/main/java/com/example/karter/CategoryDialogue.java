package com.example.karter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

public class CategoryDialogue extends DialogFragment {


    public  interface CategorySelected{
        void onCategorySelectedResult(String category);
    }

    public static final  String CALLING_ACTIVITY = "activity";

    private ListView categories_list;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.category_dialogue_layout,null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view);

        ArrayList<String> allCategories = Utils.getAllCategories();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,allCategories);
        categories_list = view.findViewById(R.id.category_dialogue_list);

        categories_list.setAdapter(adapter);

        Bundle bundle = getArguments();
        if (bundle!= null){
            String activity = bundle.getString(CALLING_ACTIVITY);
            if (activity!=null){
                categories_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        switch (activity){
                            case "all_product":
                                Intent intent = new Intent(getActivity(),SearchActivity.class);
                                intent.putExtra("category", allCategories.get(i));
//                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                break;
                            case "search_activity":
                                try {
                                    CategorySelected categorySelected = (CategorySelected) getActivity();
                                    categorySelected.onCategorySelectedResult(allCategories.get(i));
                                    dismiss();
                                }
                                catch (ClassCastException e){
                                    e.printStackTrace();
                                }

                                break;
                            default:
                                break;
                        }
                    }
                });
            }
        }

        return builder.create();
    }
}
