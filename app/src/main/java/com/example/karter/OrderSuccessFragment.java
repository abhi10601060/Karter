package com.example.karter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class OrderSuccessFragment extends Fragment {

    private Button btn_continue , btn_retry;
    private RelativeLayout if_success, if_failed;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_successful_fragment_layout,container,false);

        initViews(view);

        Bundle bundle = getArguments();
        if (bundle!=null){
            String status = bundle.getString("order_key");
            if (status.equals("success")){
                if_failed.setVisibility(View.GONE);
                if_success.setVisibility(View.VISIBLE);

                btn_continue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), AllProducts.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });


            }
            else{
                if_failed.setVisibility(View.VISIBLE);
                if_success.setVisibility(View.GONE);

                btn_retry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        CartFragment cartFragment = new CartFragment();
                        transaction.replace(R.id.cart_activity_fragment_container,cartFragment);
                        transaction.commit();
                    }
                });
            }
        }
        
        return view;
    }

    private void initViews(View view) {

        btn_continue = view.findViewById(R.id.btn_continue_shopping);
        btn_retry=view.findViewById(R.id.btn_try_again);

        if_success=view.findViewById(R.id.if_success);
        if_failed=view.findViewById(R.id.if_failure);

    }
}
