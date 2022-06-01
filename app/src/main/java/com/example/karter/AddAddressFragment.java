package com.example.karter;

import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class AddAddressFragment extends Fragment {

    private EditText name, contact_no,email,zip,address;
    private TextView warning;
    private Button add_btn;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_address_fragment_layout,container,false);
        initViews(view);

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateData()==false){
                    warning.setText("Fill All The Blanks...");
                    warning.setVisibility(View.VISIBLE);
                }
                else {
                    warning.setVisibility(View.GONE);
                    Utils.addAddress(getActivity(),new Address(name.getText().toString(),address.getText().toString(),zip.getText().toString(),
                            email.getText().toString(),contact_no.getText().toString()));

                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("address",Utils.getAllAddresses(getActivity()));

                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    AllAddressesFragment allAddressesFragment = new AllAddressesFragment();
                    allAddressesFragment.setArguments(bundle);
                    transaction.replace(R.id.cart_activity_fragment_container,allAddressesFragment);
                    transaction.commit();
                }
            }
        });
        return view;
    }

    private void initViews(View view) {
        name= view.findViewById(R.id.add_address_edt_name);
        email= view.findViewById(R.id.add_address_edt_email);
        contact_no= view.findViewById(R.id.add_address_edt_contact_no);
        zip= view.findViewById(R.id.add_address_edt_zip);
        address= view.findViewById(R.id.add_address_edt_address);

        warning = view.findViewById(R.id.add_address_txt_warning);

        add_btn=view.findViewById(R.id.add_address_btn_add);


    }
    private boolean validateData(){
        if (name.getText().toString().equals("") || email.getText().toString().equals("") ||
                address.getText().toString().equals("") || contact_no.getText().toString().equals("") ||
        zip.getText().toString().equals("")){
            return false;
        }
        else {
            return true;
        }
    }
}
