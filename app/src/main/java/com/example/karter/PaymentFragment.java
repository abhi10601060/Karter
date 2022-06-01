package com.example.karter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PaymentFragment extends Fragment {

    private TextView name, item_list,address , contact_details,total_amount;
    private Button btn_place_order;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.payment_fragment_layout,container,false);

        initViews(view);
        return view;
    }

    private void initViews(View view) {
        name=view.findViewById(R.id.payment_fragment_name);
        address=view.findViewById(R.id.payment_fragment_address);
        contact_details=view.findViewById(R.id.payment_fragment_contact_details);
        item_list=view.findViewById(R.id.payment_fragment_item_list);
        total_amount=view.findViewById(R.id.payment_fragment_total_amount);

        btn_place_order=view.findViewById(R.id.payment_fragment_btn_place_order);


    }
}
