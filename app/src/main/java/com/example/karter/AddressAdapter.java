package com.example.karter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {

    public interface RemoveAddress{
        void onAddressRemoved(Address address);
    }

    public interface AddressSelected{
        void onAddressSelected(Address address);
    }

    private ArrayList<Address> myAddresses;
    private Context context;

    public AddressAdapter(Context context) {
        this.context = context;
    }

    public void setMyAddresses(ArrayList<Address> myAdrdresses) {
        this.myAddresses = myAdrdresses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(myAddresses.get(position).getName());
        holder.contact.setText(myAddresses.get(position).getContactNo()+" / "+ myAddresses.get(position).getEmail());
        holder.address.setText(myAddresses.get(position).getAddress());

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 01-06-2022 move to checkoutFragment

                try {
                    AddressSelected addressSelected =(AddressSelected) context;
                    addressSelected.onAddressSelected(myAddresses.get(holder.getAdapterPosition()));
                }
                catch (ClassCastException e){
                    e.printStackTrace();
                }


            }
        });

        holder.parent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setMessage("Do you want to delete this Address...")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                try {
                                    RemoveAddress removeAddress = (RemoveAddress) context;
                                    removeAddress.onAddressRemoved(myAddresses.get(holder.getAdapterPosition()));
                                    notifyDataSetChanged();
                                }
                                catch (ClassCastException e){
                                    e.printStackTrace();
                                }
                            }
                        });

                builder.create().show();

                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return myAddresses.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder{
        private RelativeLayout parent;
        private TextView name , contact, address;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.address_parent_RL);

            name=itemView.findViewById(R.id.address_name);
            contact=itemView.findViewById(R.id.address_contact_details);
            address=itemView.findViewById(R.id.address_address);
        }
    }
}
