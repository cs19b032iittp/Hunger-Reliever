package com.charan.hungerreliever;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class DonorItemSelect extends AppCompatActivity {

    private TextView donorName, donorMobile, donorAddress, foodName, foodQuantity, foodAdder;
    private String donor, email, mobile,address,quantity ,city, food ,adder;
    private Button accept;
    FirebaseFirestore db ,db2,db3;
    FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_donor);

        Bundle extras = getIntent().getExtras();

        if(extras!=null){
            email = extras.getString("email");
            quantity = extras.getString("quantity");
            city = extras.getString("city");
        }

        db = FirebaseFirestore.getInstance();
        db2 = FirebaseFirestore.getInstance();
        db3 = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        accept = findViewById(R.id.accept_donation_btn);

        db.collection("profiles")
                .whereEqualTo("email",email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> profile = document.getData();
                                mobile = String.valueOf(profile.get("phone"));
                                donor = String.valueOf(profile.get("name"));
                                db2.collection("food donations")
                                        .whereEqualTo("email",email)
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if(task.isSuccessful()){
                                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                                        Map<String, Object> profile = document.getData();
                                                        food = String.valueOf(profile.get("name"));
                                                        adder = String.valueOf(profile.get("adders"));
                                                        address = String.valueOf(profile.get("address")) + " , " + city;

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

                                                        foodName.setText(food);
                                                        foodQuantity.setText(quantity);
                                                        foodAdder.setText(adder);
                                                    }
                                                }
                                                else{
                                                    Toast.makeText(DonorItemSelect.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                    finish();
                                                }
                                            }
                                        });


                            }
                        }
                        else{
                            Toast.makeText(DonorItemSelect.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });


        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DocumentReference documentReference = db3.collection("food donations").document(email);
                Map<String,Object> data = new HashMap<>();
                data.put("org_email",auth.getCurrentUser().getEmail());

                documentReference.set(data, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        documentReference.update("status",true);
                        Toast.makeText(DonorItemSelect.this, "Added to donations list", Toast.LENGTH_SHORT).show();

                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DonorItemSelect.this, "Please Try Later.\n"+ e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}
