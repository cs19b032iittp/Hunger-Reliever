package com.charan.hungerreliever;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db1;
    private FirebaseFirestore db2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        db1 = FirebaseFirestore.getInstance();
        db2 = FirebaseFirestore.getInstance();

        // launching a splash activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(FirebaseAuth.getInstance().getCurrentUser() != null){
                    db1.collection("profiles")
                            .whereEqualTo("email",firebaseAuth.getCurrentUser().getEmail())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Map<String, Object> profile = document.getData();
                                            if(String.valueOf(profile.get("user")).equals("1")){
                                                startActivity(new Intent(new Intent(getApplicationContext(),UserDashboard.class)));
                                                finish();
                                            }
                                            else {
                                                organisationStatus();
                                            }
                                        }
                                    } else {
                                        Toast.makeText(MainActivity.this, "Please try again later", Toast.LENGTH_SHORT).show();
                                        Log.d("MainActivity", "Error getting documents: ", task.getException());
                                    }
                                }
                            });
                }
                else {
                    Intent intent = new Intent(MainActivity.this,Login.class);
                    startActivity(intent);
                    finish();
                }
            }
        },0);
   }

    private void organisationStatus() {
        db2.collection("verifyOrganisations")
                .whereEqualTo("email",firebaseAuth.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> profile = document.getData();
                                if(String.valueOf(profile.get("formSubmitted")).equals("0")){
                                    startActivity(new Intent(new Intent(getApplicationContext(),OrganisationDetailsForm.class)));
                                    finish();
                                }
                                else if(String.valueOf(profile.get("verificationStatus")).equals("0")){
                                    startActivity(new Intent(new Intent(getApplicationContext(),VerificationStatus.class)));
                                    finish();
                                }
                                else {
                                    startActivity(new Intent(new Intent(getApplicationContext(),Orgdashboard.class)));
                                    finish();
                                }
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Please try again later", Toast.LENGTH_SHORT).show();
                            Log.d("MainActivity", "Error getting documents: "+ task.getException());
                        }
                    }
                });
    }
}