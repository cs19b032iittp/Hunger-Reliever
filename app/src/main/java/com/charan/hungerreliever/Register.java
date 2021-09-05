package com.charan.hungerreliever;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

// for creating an account
public class Register extends AppCompatActivity {

    // Declaring required variables
    private EditText name, email, password, phone;
    private TextView loginText;
    private Button button;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private RadioGroup radioGroup;
    private RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize the declared variables
        Initialize();

        // If "Login Text" was clicked redirect to Login Page
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

        // If "Continue button" was clicked ...
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Hide the soft input Keyboard
                InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

                progressBar.setVisibility(View.VISIBLE);


                // To fetch entered details
                int id = radioGroup.getCheckedRadioButtonId();
                radioButton =  findViewById(id);
                String Name = name.getText().toString();
                String Email =  email.getText().toString();
                String Phone = phone.getText().toString();
                String PhonePattern = "^[6-9]\\d{9}$";
                String user = "1";
                String Password = password.getText().toString();

                // If he/she registers for an organisation
                if(!radioButton.getText().equals("User")){
                    user = "0";
                }

                // To check text in any input box was left empty or it doesn't match pattern to that specific box : up to line 154
                if(TextUtils.isEmpty(Name)){
                    progressBar.setVisibility(View.INVISIBLE);
                    name.setError("Name is required.");
                    name.requestFocus();
                    return;
                }


                if(TextUtils.isEmpty(Phone)){
                    progressBar.setVisibility(View.INVISIBLE);
                    phone.setError("Phone Number is required.");
                    phone.requestFocus();
                    return;
                }

                if(!Phone.matches(PhonePattern)){
                    progressBar.setVisibility(View.INVISIBLE);
                    phone.setError("Please, Provide a valid phone number");
                    phone.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(Email)){
                    progressBar.setVisibility(View.INVISIBLE);
                    email.setError("Email is required.");
                    email.requestFocus();
                    return;
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
                    progressBar.setVisibility(View.INVISIBLE);
                    email.setError("Please, Provide a valid email");
                    email.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(Password)){
                    progressBar.setVisibility(View.INVISIBLE);
                    password.setError("Password is required.");
                    password.requestFocus();
                    return;
                }

                if(Password.length()<6){
                    progressBar.setVisibility(View.INVISIBLE);
                    password.setError("Password length must be > 6.");
                    password.requestFocus();
                    return;

                }

                // Creating a bundle to pass user details to through further activities.
                Bundle bundle = new Bundle();
                bundle.putString("Name",Name);
                bundle.putString("Phone",Phone);
                bundle.putString("Email",Email);
                bundle.putString("Password",Password);
                bundle.putString("User",user);

                // To check user is already registered or not
                auth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        //if this task is successful user is not registered else user registered until user gives the new email id
                        if(task.isSuccessful()){

                            // now account was to be  deleted as we want the user phone number for verification
                            auth.getCurrentUser().delete();

                            // now user will be redirected to send OTP page
                            progressBar.setVisibility(View.GONE);
                            Intent intent = new Intent(getApplicationContext(),SendOTP.class);
                            intent.putExtras(bundle); // passing user data to another activity
                            startActivity(intent);
                            finish();

                        }
                        else {
                            Toast.makeText(Register.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        });
    }

    // method to initialize the declared variables
    private void Initialize() {
        name = findViewById(R.id.nameRegister);
        email = findViewById(R.id.emailRegister);
        password = findViewById(R.id.PasswordRegister);
        phone = findViewById(R.id.phoneRegister);
        loginText = findViewById(R.id.textLoginButton);
        button = findViewById(R.id.buttonContinue);
        progressBar =findViewById(R.id.progressBarRegister);
        radioGroup = findViewById(R.id.radioGroup);
        auth = FirebaseAuth.getInstance();
    }

}