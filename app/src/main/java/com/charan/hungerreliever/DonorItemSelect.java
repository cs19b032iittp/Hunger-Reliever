package com.charan.hungerreliever;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DonorItemSelect extends AppCompatActivity {

    private TextView donorName, donorMobile, donorAddress, foodName, foodQuantity, foodAdder;
    private Button accept;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_donor);

        String donor = null;
        String mobile = null;
        String address = null;

        Bundle extras = getIntent().getExtras();

        if(extras!=null){
            donor = extras.getString("name");
            mobile = extras.getString("mobile");
            address = extras.getString("address");
        }

        donorName = findViewById(R.id.donor_name);
        donorAddress = findViewById(R.id.donor_address);
        donorMobile = findViewById(R.id.donor_mobile);
        foodName = findViewById(R.id.donor_food_name);
        foodQuantity = findViewById(R.id.donor_food_quantity);
        foodAdder = findViewById(R.id.donor_food_adders);
        accept = findViewById(R.id.accept_donation_btn);

        donorName.setText(donor);
        donorMobile.setText(mobile);
        donorAddress.setText(address);

    }
}
