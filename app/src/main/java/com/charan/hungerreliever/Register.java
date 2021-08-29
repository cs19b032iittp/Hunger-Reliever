package com.charan.hungerreliever;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Register extends AppCompatActivity {

    private EditText name, email, password, phone;
    private TextView loginText;
    private Button button;
    private ProgressBar progressBar;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.nameRegister);
        email = findViewById(R.id.emailRegister);
        password = findViewById(R.id.PasswordRegister);
        phone = findViewById(R.id.phoneRegister);
        loginText = findViewById(R.id.textLoginButton);
        button = findViewById(R.id.buttonContinue);
        progressBar =findViewById(R.id.progressBarRegister);


        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                Intent intent = new Intent(Register.this,Login.class);
                startActivity(intent);
                finish();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                //firebaseDatabase = FirebaseDatabase.getInstance();
                //databaseReference = firebaseDatabase.getReference("Users");

                String  Name = name.getText().toString();
                String Email =  email.getText().toString();
                String Phone = phone.getText().toString();
                String Password = password.getText().toString();


                if(TextUtils.isEmpty(Name)){
                    progressBar.setVisibility(View.INVISIBLE);
                    name.setError("Name is Required.");
                    name.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(Email)){
                    progressBar.setVisibility(View.INVISIBLE);
                    email.setError("Email is Required.");
                    email.requestFocus();
                    return;
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
                    progressBar.setVisibility(View.INVISIBLE);
                    email.setError("Please Provide valid email");
                    email.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(Password)){
                    progressBar.setVisibility(View.INVISIBLE);
                    password.setError("Password is Required.");
                    password.requestFocus();
                    return;
                }

                if(Password.length()<6){
                    progressBar.setVisibility(View.INVISIBLE);
                    password.setError("Password length must be > 6.");
                    password.requestFocus();
                    return;

                }

                Bundle bundle = new Bundle();
                bundle.putString("Name",Name);
                bundle.putString("Phone",Phone);
                bundle.putString("Email",Email);
                bundle.putString("Password",Password);

                Intent intent = new Intent(getApplicationContext(),SendOTP.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();

                progressBar.setVisibility(View.INVISIBLE);



                //UserHelper helper = new UserHelper(Name,Email,Phone);
                // databaseReference.setValue(helper);

            }
        });

    }
}