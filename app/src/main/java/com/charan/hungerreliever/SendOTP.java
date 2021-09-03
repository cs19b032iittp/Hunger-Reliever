package com.charan.hungerreliever;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.FileObserver;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class SendOTP extends AppCompatActivity {

    // Declaring required variables
    private Button button;
    private FirebaseAuth auth;
    private  Bundle bundle;
    private ProgressBar progressBar;
    private  String name ,email, phone, password, user;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_otp);

        // Initialize the declared variables
        Initialize();

        // If user clicks the send otp button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);

                PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber("+91" + phone)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(SendOTP.this)
                        .setCallbacks(mCallBacks)
                        .build();

                PhoneAuthProvider.verifyPhoneNumber(options);


            }
        });

        // Initializing the call backs
        mCallBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            // If user's phone number (sim) is in the device he is using now, He will be verified without asking him to enter OTP
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                progressBar.setVisibility(View.INVISIBLE);
                VerificationCompleted();
            }

            // If verification is failed , user will be redirected to register page.
            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(SendOTP.this, "Verification Failed" + e.getMessage(), Toast.LENGTH_LONG).show();
                startActivity(new Intent(SendOTP.this,Register.class));
                finish();
            }

            // this method will be useful if given phone number was on another device
            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                Toast.makeText(SendOTP.this, "OTP Sent to your number", Toast.LENGTH_LONG).show();

                // 10 seconds delay to perform auto verification
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(auth.getCurrentUser() == null){

                            progressBar.setVisibility(View.INVISIBLE);
                            Bundle bundle1 =new Bundle();
                            bundle1.putString("Name",name);
                            bundle1.putString("Phone",phone);
                            bundle1.putString("Email",email);
                            bundle1.putString("Password",password);
                            bundle1.putString("User",user);
                            bundle1.putString("auth",s); // verification code token was required verify the user manually

                            // redirecting user to another activity to ask him to enter OTP
                            Intent intent = new Intent(SendOTP.this,VerifyPhoneNumber.class);
                            intent.putExtras(bundle1);
                            startActivity(intent);
                            finish();

                        }

                    }
                },10000);



            }
        };

    }

    // method to initialize the declared variables
    private void Initialize() {
        button = findViewById(R.id.buttonSendCode);
        auth = FirebaseAuth.getInstance();
        bundle = getIntent().getExtras();
        name = bundle.getString("Name");
        phone = bundle.getString("Phone");
        email = bundle.getString("Email");
        password = bundle.getString("Password");
        user = bundle.getString("User");
        progressBar = findViewById(R.id.progressbarSendOTP);
        firestore = FirebaseFirestore.getInstance();
    }

    // method to register user in our database , If he was verified.
    private void VerificationCompleted() {

        // creating user authentication  account
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                // if task is successful , now we need to create user profile else redirect him to registration page.
                if(task.isSuccessful()){

                    createProfile();

                }
                else {
                    if(auth.getCurrentUser()!=null){
                        auth.getCurrentUser().delete();
                    }

                    Toast.makeText(SendOTP.this, "Error! Account Not Created.\n" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SendOTP.this,Register.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void createProfile() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        ProfileClass profile = new ProfileClass(name,email,phone,user);
        db.collection("profiles").document(auth.getCurrentUser().getUid()).set(profile).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                if(user.equals("1")){
                    createUser();
                }
                else{
                    createOrganisation();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(auth.getCurrentUser()!=null){
                    auth.getCurrentUser().delete();
                }
                Toast.makeText(SendOTP.this, "Registration Unsuccessful! Please try Again.", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                Intent intent = new Intent(SendOTP.this,Register.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });


    }

    private void createOrganisation() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        OrganisationClass organisation = new OrganisationClass(name,email,phone,false,false);
        db.collection("verifyOrganisations").document(auth.getCurrentUser().getUid()).set(organisation).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(SendOTP.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                Intent intent = new Intent(SendOTP.this,OrganisationDetailsForm.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(auth.getCurrentUser()!=null){
                    auth.getCurrentUser().delete();
                }
                Toast.makeText(SendOTP.this, "Registration Unsuccessful !\n"+e.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                Intent intent = new Intent(SendOTP.this,Register.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });

    }

    private void createUser() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        UserClass user = new UserClass(name,email,phone);
        db.collection("users").document(auth.getCurrentUser().getUid()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(SendOTP.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                Intent intent = new Intent(SendOTP.this,UserDashboard.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(auth.getCurrentUser()!=null){
                    auth.getCurrentUser().delete();
                }
                Toast.makeText(SendOTP.this, "Registration Unsuccessful !\n"+e.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                Intent intent = new Intent(SendOTP.this,Register.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });

    }


    /*
    // method to create  profile in database
    private void createProfile() {
        DocumentReference documentReference =firestore.collection("profiles").document(auth.getCurrentUser().getUid());
        Map<String,Object> data = new HashMap<>();
        data.put("name",name);
        data.put("phone",phone);
        data.put("email",email);
        data.put("user",user);

        // If successful call respective methods else return him to registration page
        documentReference.set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                if(user.equals("1")){
                    createUserProfile();
                }

                else{
                    createOrganisationProfile();
                }
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                if(auth.getCurrentUser()!=null){
                    auth.getCurrentUser().delete();
                }
                Toast.makeText(SendOTP.this, "Registration Unsuccessful !\n"+e.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                Intent intent = new Intent(SendOTP.this,Register.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
    */

    /*
    private void createOrganisationProfile() {
        DocumentReference documentReference =firestore.collection("VerifyOrganisations").document(auth.getCurrentUser().getUid());
        Map<String,Object> data = new HashMap<>();
        data.put("name",name);
        data.put("phone",phone);
        data.put("email",email);
        data.put("verificationStatus","0");

        // If successful redirect  to dashboard else return him to registration page
        documentReference.set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                Toast.makeText(SendOTP.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                Intent intent = new Intent(SendOTP.this,OrganisationDetailsForm.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                if(auth.getCurrentUser()!=null){
                    auth.getCurrentUser().delete();
                }
                Toast.makeText(SendOTP.this, "Registration Unsuccessful !\n"+e.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                Intent intent = new Intent(SendOTP.this,Register.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
    */

    /*
    private void createUserProfile() {
        DocumentReference documentReference =firestore.collection("Users").document(auth.getCurrentUser().getUid());
        Map<String,Object> data = new HashMap<>();
        data.put("name",name);
        data.put("phone",phone);
        data.put("email",email);

        // If successful redirect  to dashboard else return him to registration page
        documentReference.set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                if(user.equals("1")){
                    createUserProfile();
                }

                else{
                    createOrganisationProfile();
                }

                Toast.makeText(SendOTP.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                Intent intent = new Intent(SendOTP.this,UserDashboard.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                if(auth.getCurrentUser()!=null){
                    auth.getCurrentUser().delete();
                }
                Toast.makeText(SendOTP.this, "Registration Unsuccessful !\n"+e.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                Intent intent = new Intent(SendOTP.this,Register.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
    */

}