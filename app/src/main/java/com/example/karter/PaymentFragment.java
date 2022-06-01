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

import java.util.ArrayList;

public class PaymentFragment extends Fragment {

    private TextView name, item_list,address , contact_details,total_amount;
    private Button btn_place_order;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.payment_fragment_layout,container,false);

        initViews(view);
        Bundle bundle = getArguments();
        if (bundle!= null){
            Address incomingAddress = bundle.getParcelable("payment_address");

            if (address!=null){

                name.setText(incomingAddress.getName());
                address.setText(incomingAddress.getAddress()+" - "+incomingAddress.getZipCode());
                contact_details.setText(incomingAddress.getContactNo()+" / "+incomingAddress.getEmail());

                ArrayList<CartItem> myCart = Utils.getAllCartItems(getActivity());

                double total = 0;
                int idx =1;
                for (CartItem i : myCart){
                    item_list.setText(item_list.getText()+"\n"+
                            idx+". "+ i.getItem().getName()+"  (x"+i.getQuantity()+")");
                    total+=i.getItem().getPrice()*i.getQuantity();
                    idx++;
                }

                total_amount.setText("\u20B9"+total);





            }



        }
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
