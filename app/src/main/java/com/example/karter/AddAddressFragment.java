package com.example.karter;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddAddressFragment extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private static  final  String REVIEW_COLLECTION = "Reviews";
    private static final String ALL_GROCERY_ITEMS_COLLECTION = "AllGroceryItems";
    private static final String ALL_USERS_COLLECTION = "Users";
    private static final String USER_CART_COLLECTION = "Cart";
    private static final String USER_ADDRESS_COLLECTION = "Addresses";

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
                   Address userAddress =new Address(name.getText().toString(),address.getText().toString(),zip.getText().toString(),
                            email.getText().toString(),contact_no.getText().toString());

                   checkUserExist(userAddress);
                }
            }
        });
        return view;
    }
    private void checkUserExist(Address address1) {
        
        db.collection(ALL_USERS_COLLECTION).document(user.getUid())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (!documentSnapshot.exists()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                            .setTitle("Complete Profile")
                            .setMessage("Please complete your profile before adding items to cart...")
                            .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    startActivity(new Intent(getActivity(),UpdateProfileActivity.class));
                                }
                            });
                    builder.create().show();
                }
                else{
                    addAddressToFirestore(address1);
                }
            }
        });

    }

    private void addAddressToFirestore(Address userAddress) {

        db.collection(ALL_USERS_COLLECTION).document(user.getUid()).collection(USER_ADDRESS_COLLECTION)
                .add(userAddress).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getActivity(), "Your Address Saved successfully...", Toast.LENGTH_SHORT).show();

                    DocumentReference doc = task.getResult();
                    String dbId = doc.getId();
                    doc.update("dbId",dbId);

                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    AllAddressesFragment allAddressesFragment = new AllAddressesFragment();
                    transaction.replace(R.id.cart_activity_fragment_container,allAddressesFragment);
                    transaction.commit();
                }
                else {
                    Toast.makeText(getActivity(), "Something Went Wrong....", Toast.LENGTH_SHORT).show();
                }
            }
        });

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
