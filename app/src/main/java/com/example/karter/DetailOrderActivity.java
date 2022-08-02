package com.example.karter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DetailOrderActivity extends AppCompatActivity {
    private TextView desc , date,name ,address ,contact , paymentMethod,totalPrice, tax, totalAmount;
    private RecyclerView itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);

        Intent intent = getIntent();
        if (intent!=null){
           Order incomingOrder = intent.getParcelableExtra("incomingOrder");
           Bundle bundle = intent.getBundleExtra("bundle");

           if (incomingOrder!=null){

               initViews();
                desc.setText(incomingOrder.getDescription());
                if (bundle!=null){
                    String inDate =bundle.getString("date");
                    date.setText(inDate);
                }

               name.setText(incomingOrder.getAddress().getName());
               address.setText(incomingOrder.getAddress().getAddress()+" - "+incomingOrder.getAddress().getZipCode());
               contact.setText(incomingOrder.getAddress().getContactNo() + " / " + incomingOrder.getAddress().getEmail());
               paymentMethod.setText(incomingOrder.getPaymentMethod());
               totalAmount.setText("\u20B9" + incomingOrder.getTotalAmount());

               if (bundle!=null){
                   ArrayList<CartItem> cart = new ArrayList<>();
                   cart=bundle.getParcelableArrayList("cart");
                   double totalP = 0;
                   for(CartItem item : cart){
                       totalP+=item.getTotalPrice();
                   }
                   totalPrice.setText("\u20B9" + totalP);
                   double tx = totalP*10.0/100.0;
                   tax.setText("\u20B9"+tx);


                   OrderListAdapter adapter = new OrderListAdapter(this);
                   adapter.setCart(cart);
                   itemList.setAdapter(adapter);
                   itemList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

               }


           }

        }

    }

    private void initViews() {
        desc = findViewById(R.id.order_detail_description);
        date=findViewById(R.id.order_detail_date_txt);
        name=findViewById(R.id.order_details_name);
        address=findViewById(R.id.order_details_address);
        contact=findViewById(R.id.order_details_contact_details);
        paymentMethod=findViewById(R.id.order_details_payment_option);
        totalPrice=findViewById(R.id.order_details_total_price);
        tax=findViewById(R.id.order_details_tax);
        totalAmount=findViewById(R.id.order_details_total_amount);
        itemList = findViewById(R.id.order_list_RV);
    }
}