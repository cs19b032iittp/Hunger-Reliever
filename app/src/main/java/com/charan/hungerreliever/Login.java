package com.charan.hungerreliever;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class Login extends AppCompatActivity {


    private static final String TAG = "TAG" ;
    // Declaring required variables
    private EditText email, password;
    private TextView registerText,forgotPasswordText;
    private ProgressBar progressBar;
    private Button button;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db1;
    private FirebaseFirestore db2;

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
                finish() ;
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
                            login();
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

    private void login() {
        db1.collection("profiles")
                .whereEqualTo("email", firebaseAuth.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> profile = document.getData();
                                if (String.valueOf(profile.get("user")).equals("1")) {
                                    startActivity(new Intent(getApplicationContext(), UserDashboard.class));
                                    finish();
                                }
                                else {
                                    organisationStatus();
                                }
                            }
                        } else {
                            Toast.makeText(Login.this, "Please Try Again Later!", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });
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
                            Toast.makeText(Login.this, "Please try again later", Toast.LENGTH_SHORT).show();
                            Log.d("MainActivity", "Error getting documents: "+ task.getException());
                        }
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
        db1 = FirebaseFirestore.getInstance();
        db2 = FirebaseFirestore.getInstance();
    }

}