package com.charan.hungerreliever;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class DashBoard extends AppCompatActivity {

    private Button button;
    private FirebaseAuth firebaseAuth;
    private  FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        button = findViewById(R.id.buttonDashboard);
        firebaseAuth = FirebaseAuth.getInstance();



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(firebaseAuth.getCurrentUser() != null){
                    FirebaseAuth.getInstance().signOut();
                    button.setText("Login");
                }
                else{
                    startActivity(new Intent(new Intent(getApplicationContext(),Login.class)));
                    finish();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(firebaseAuth.getCurrentUser() != null){
            button.setText("LogOut");
        }
        else{
            button.setText("Login");
        }
    }
}