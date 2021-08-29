package com.charan.hungerreliever;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private EditText email, password;
    private TextView registerText,forgotPasswordText;
    private ProgressBar progressBar;
    private Button button;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onStart() {
        super.onStart();
        email.setFocusable(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registerText = findViewById(R.id.textRegisterButton);
        email = findViewById(R.id.emailLogin);
        password = findViewById(R.id.PasswordLogin);
        progressBar = findViewById(R.id.progressBarLogin);
        button = findViewById(R.id.buttonLogin);
        forgotPasswordText = findViewById(R.id.textForgotPassword);
        firebaseAuth = FirebaseAuth.getInstance();


        forgotPasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = email.getText().toString();
                progressBar.setVisibility(View.VISIBLE);
                Intent intent = new Intent(getApplicationContext(),Reset.class);
                intent.putExtra("email",mail);
                startActivity(intent);
                finish();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                Intent intent = new Intent(Login.this,Register.class);
                startActivity(intent);
                progressBar.setVisibility(View.GONE);
                finish();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email =  email.getText().toString();
                String Password = password.getText().toString();

                if(TextUtils.isEmpty(Email)){
                    email.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(Password)){
                    password.setError("Password is Required.");
                    return;
                }

                if(Password.length()<6){
                    password.setError("Password length must be > 6.");
                    return;

                }

                progressBar.setVisibility(View.VISIBLE);

                firebaseAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(Login.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), DashBoard.class));
                            finish();
                        }
                        else {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(Login.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}