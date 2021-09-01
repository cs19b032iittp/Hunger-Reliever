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
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    //cs19b032

    private EditText email, password;
    private TextInputLayout mail,pwd;
    private TextView registerText,forgotPasswordText;
    private ProgressBar progressBar;
    private Button button;
    private FirebaseAuth firebaseAuth;

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

        mail = findViewById(R.id.emailLoginLayout);
        pwd = findViewById(R.id.loginPasswordLayout);
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
                InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                String Email =  email.getText().toString();
                String Password = password.getText().toString();

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

                firebaseAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(Login.this, "Log In Successful", Toast.LENGTH_SHORT).show();
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

}