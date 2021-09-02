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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {


    // Declaring required variables
    private EditText email, password;
    private TextView registerText,forgotPasswordText;
    private ProgressBar progressBar;
    private Button button;
    private FirebaseAuth firebaseAuth;

    // If user is logged in redirect to dashboard
    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            startActivity(new Intent(new Intent(getApplicationContext(),UserDashboard.class)));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize the declared variables
        Initialize();

        // If "Forgot Password" was clicked redirect to Reset Page
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

        // If  "Create an Account" was clicked redirect to Registration Page
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


        // If "Login button" was clicked ...
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Hide the soft input Keyboard
                InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

                String Email =  email.getText().toString();
                String Password = password.getText().toString();

                // To check text in any input box was left empty or it doesn't match pattern to that specific box
                if(TextUtils.isEmpty(Email)){
                    email.setError("Email is Required.");
                    return;
                }

                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
                    email.setError("Please provide an valid email address");
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

                // user authentication
                firebaseAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            //If  Login Successful redirect to dashboard
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), UserDashboard.class));
                            finish();
                        }
                        else {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(Login.this,task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    // method to initialize the declared variables
    private void Initialize() {
        registerText = findViewById(R.id.textRegisterButton);
        email = findViewById(R.id.emailLogin);
        password = findViewById(R.id.PasswordLogin);
        progressBar = findViewById(R.id.progressBarLogin);
        button = findViewById(R.id.buttonLogin);
        forgotPasswordText = findViewById(R.id.textForgotPassword);
        firebaseAuth = FirebaseAuth.getInstance();
    }

}