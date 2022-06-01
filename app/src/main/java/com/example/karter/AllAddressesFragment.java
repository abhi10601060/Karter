package com.example.karter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AllAddressesFragment extends Fragment {

    private RelativeLayout if_empty , all_addresses;
    private FloatingActionButton btn_add;
    private RecyclerView address_RV;

    private ArrayList<Address> myAddresses;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.adresses_fragment_layout,container,false);
        initViews(view);

        handleAddAddress();
        Bundle bundle = getArguments();
        if (bundle!= null){
            myAddresses=bundle.getParcelableArrayList("address");
        }


        if (myAddresses==null){
            if_empty.setVisibility(View.VISIBLE);
            all_addresses.setVisibility(View.GONE);
        }
        else{
            if_empty.setVisibility(View.GONE);
            all_addresses.setVisibility(View.VISIBLE);
            handleAddresses();
        }




        return view;
    }

    private void initViews(View view) {
        if_empty= view.findViewById(R.id.addresses_fragment_if_empty);
        all_addresses= view.findViewById(R.id.addresses_RL);

        btn_add = view.findViewById(R.id.add_address_floating_btn);

        address_RV=view.findViewById(R.id.addresses_RV);
    }

    private void handleAddresses(){
        AddressAdapter adapter = new AddressAdapter(getActivity());
        adapter.setMyAddresses(myAddresses);
        address_RV.setAdapter(adapter);
        address_RV.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

    }
    private void handleAddAddress(){
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                AddAddressFragment addAddressFragment = new AddAddressFragment();
                transaction.replace(R.id.cart_activity_fragment_container,addAddressFragment);
                transaction.commit();
            }
        });
    }
}
