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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AllAddressesFragment extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private static  final  String REVIEW_COLLECTION = "Reviews";
    private static final String ALL_GROCERY_ITEMS_COLLECTION = "AllGroceryItems";
    private static final String ALL_USERS_COLLECTION = "Users";
    private static final String USER_CART_COLLECTION = "Cart";
    private static final String USER_ADDRESS_COLLECTION = "Addresses";

    private RelativeLayout if_empty , all_addresses;
    private FloatingActionButton btn_add;
    private RecyclerView address_RV;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.adresses_fragment_layout,container,false);
        initViews(view);

        ArrayList<Address> myAddresses = new ArrayList<>();
        AddressAdapter adapter = new AddressAdapter(getActivity());
        adapter.setMyAddresses(myAddresses);
        address_RV.setAdapter(adapter);
        address_RV.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        db.collection(ALL_USERS_COLLECTION).document(user.getUid()).collection(USER_ADDRESS_COLLECTION)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()){

                    if_empty.setVisibility(View.GONE);
                    all_addresses.setVisibility(View.VISIBLE);

                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots){
                        Address address = doc.toObject(Address.class);
                        myAddresses.add(address);
                    }
                    adapter.notifyDataSetChanged();
                }
                else{
                    if_empty.setVisibility(View.VISIBLE);
                    all_addresses.setVisibility(View.GONE);
                }
            }
        });

        handleAddAddress();

        return view;
    }

    private void initViews(View view) {
        if_empty= view.findViewById(R.id.addresses_fragment_if_empty);
        all_addresses= view.findViewById(R.id.addresses_RL);

        btn_add = view.findViewById(R.id.add_address_floating_btn);

        address_RV=view.findViewById(R.id.addresses_RV);
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
