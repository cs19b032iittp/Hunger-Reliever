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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class Reset extends AppCompatActivity {

    // Declaring required variables
    private EditText email;
    private TextView cancelText;
    private Button resetBtn;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;
    private String mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        // Initialize the declared variables
        Initialize();

        // If "Send button" was clicked reset link will be sent to their given email
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                String rEmail = email.getText().toString();

                if(TextUtils.isEmpty(rEmail)){
                    email.setError("Email is Required.");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                firebaseAuth.sendPasswordResetEmail(rEmail).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(Reset.this,"Reset Link sent to your Mail",Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        startActivity(new Intent(getApplicationContext(),Login.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Reset.this,"Error"+ e.getMessage(),Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        startActivity(new Intent(getApplicationContext(),Login.class));
                        finish();
                    }
                });

            }
        });

        cancelText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                startActivity(new Intent(getApplicationContext(),Login.class));
                progressBar.setVisibility(View.INVISIBLE);
                finish();
            }
        });


    }

    // method to initialize the declared variables
    private void Initialize() {
        email = findViewById(R.id.emailReset);
        resetBtn = findViewById(R.id.buttonReset);
        progressBar = findViewById(R.id.progressBarReset);
        firebaseAuth = FirebaseAuth.getInstance();
        cancelText = findViewById(R.id.textCancelReset);
        mail = getIntent().getStringExtra("email");

        email.setText(mail);
    }
}