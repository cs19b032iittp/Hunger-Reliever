package com.charan.hungerreliever;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class VerificationStatus extends AppCompatActivity {
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView textView = findViewById(R.id.textView2);
//    @Override
//    protected void onStart() {
//        super.onStart();
//        if(FirebaseAuth.getInstance().getCurrentUser() != null){
//
//            db.collection("verifyOrganisations")
//                    .whereEqualTo("email",firebaseAuth.getCurrentUser().getEmail())
//                    .get()
//                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                            if (task.isSuccessful()) {
//                                for (QueryDocumentSnapshot document : task.getResult()) {
//                                    Map<String, Object> profile = document.getData();
//                                    if(String.valueOf(profile.get("verificationStatus")).equals("true")){
//                                        startActivity(new Intent(new Intent(getApplicationContext(),Orgdashboard.class)));
//                                        finish();
//                                    }
//
//                                }
//                            } else {
//                                Log.d("MainActivity", "Error getting documents: ", task.getException());
//                                Toast.makeText(VerificationStatus.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(VerificationStatus.this,Login.class);
//                                startActivity(intent);
//                                finish();
//
//                            }
//                        }
//                    });
//        }
//        else {
//            Intent intent = new Intent(VerificationStatus.this,Login.class);
//            startActivity(intent);
//            finish();
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_status);
    }
}