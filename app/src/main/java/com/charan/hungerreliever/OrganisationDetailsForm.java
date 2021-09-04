package com.charan.hungerreliever;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class OrganisationDetailsForm extends AppCompatActivity {

    // Declaring required variables
    private EditText name, phone, UPI_Id, pincode,city, state,houseNo , road;
    private ProgressBar progressBar;
    private Button button;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organisation_details_form);

        name = findViewById(R.id.nameDetails);
        phone = findViewById(R.id.phoneDetails);
        UPI_Id = findViewById(R.id.upi);
        pincode = findViewById(R.id.pincode);
        city= findViewById(R.id.City);
        state =  findViewById(R.id.state);
        houseNo = findViewById(R.id.HouseNo);
        road = findViewById(R.id.Road);
        progressBar = findViewById(R.id.progressBarDetails);
        button = findViewById(R.id.buttonGetVerified);
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        // If "button" was clicked ...
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Hide the soft input Keyboard
                InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

                String name_, phone_, UPI_Id_, pincode_,city_, state_,houseNo_ , road_;

                name_ = name.getText().toString().trim();
                phone_ = phone.getText().toString().trim();
                UPI_Id_ = UPI_Id.getText().toString().trim();
                pincode_ = pincode.getText().toString().trim();
                city_ = city.getText().toString().trim();
                state_ = state.getText().toString().trim();
                houseNo_ = houseNo.getText().toString().trim();
                road_ = road.getText().toString().trim();



                // To check text in any input box was left empty or it doesn't match pattern to that specific box

                if(TextUtils.isEmpty(UPI_Id_)){
                    UPI_Id.setError("Required.");
                    return;
                }

                if(TextUtils.isEmpty(pincode_)){
                    pincode.setError("Required.");
                    return;
                }

                if(TextUtils.isEmpty(city_)){
                    city.setError("Required.");
                    return;
                }

                if(TextUtils.isEmpty(state_)){
                    state.setError("Required.");
                    return;
                }

                if(TextUtils.isEmpty(houseNo_)){
                    houseNo.setError("Required.");
                    return;
                }

                if(TextUtils.isEmpty(road_)){
                    road.setError("Required.");
                    return;
                }


                progressBar.setVisibility(View.VISIBLE);

                String Address =  pincode_ + " , " + city_ + " , " + state_ + " , " + houseNo_  + " , " + road_;
                DocumentReference documentReference =firestore.collection("verifyOrganisations").document(auth.getCurrentUser().getUid());
                Map<String,Object> data = new HashMap<>();
                data.put("upi_id",UPI_Id_);
                data.put("address",Address);


                // If successful redirect  to dashboard else return him to registration page
                documentReference.set(data, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        documentReference.update("formSubmitted","1");
                        Toast.makeText(OrganisationDetailsForm.this, "Submitted Successfully", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        Intent intent = new Intent(OrganisationDetailsForm.this,VerificationStatus.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        finish();
                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(OrganisationDetailsForm.this, "Please Try Later.\n"+ e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }



}
