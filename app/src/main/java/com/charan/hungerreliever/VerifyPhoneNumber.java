package com.charan.hungerreliever;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class VerifyPhoneNumber extends AppCompatActivity {

    // Declaring required variables
    private Button verify_Button;
    private EditText otp;
    private String verificationCodeBySystem;
    private  FirebaseAuth auth;
    private Bundle bundle;
    private  String name ,email, phone, password,user;
    private ProgressBar progressBar;
    FirebaseFirestore firestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone_number);

        // Initialize the declared variables
        Initialize();

        // If user clicks the verify button
        verify_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);

                // to hide the soft Input keyboard
                InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

                String verification_code = otp.getText().toString();
                if(!verification_code.isEmpty()){

                    //If input is not empty
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCodeBySystem,verification_code);
                    signIn(credential);

                }
                else{
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(VerifyPhoneNumber.this, "Please Enter OTP", Toast.LENGTH_SHORT).show();
                    return;

                }
            }
        });

    }

    // method to initialize the declared variables
    private void Initialize() {
        verify_Button = findViewById(R.id.buttonVerify);
        otp = findViewById(R.id.otp);
        bundle = getIntent().getExtras();
        name = bundle.getString("Name");
        phone = bundle.getString("Phone");
        email = bundle.getString("Email");
        password = bundle.getString("Password");
        user = bundle.getString("User");
        verificationCodeBySystem = bundle.getString("auth");
        progressBar = findViewById(R.id.progressBarVerify);
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }

    private  void signIn(PhoneAuthCredential credential){
        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    auth.getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            VerificationCompleted();
                        }
                    });
                }
                else{
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(VerifyPhoneNumber.this, "User Not Verified!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
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

                    Toast.makeText(VerifyPhoneNumber.this, "Error! Account Not Created.\n" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(VerifyPhoneNumber.this,Register.class);
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
                Toast.makeText(VerifyPhoneNumber.this, "Registration Unsuccessful! Please try Again.", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                Intent intent = new Intent(VerifyPhoneNumber.this,Register.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });


    }

    private void createOrganisation() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        OrganisationClass organisation = new OrganisationClass(name,email,phone,"0","0");
        db.collection("verifyOrganisations").document(auth.getCurrentUser().getUid()).set(organisation).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(VerifyPhoneNumber.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                Intent intent = new Intent(VerifyPhoneNumber.this,OrganisationDetailsForm.class);
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
                Toast.makeText(VerifyPhoneNumber.this, "Registration Unsuccessful !\n"+e.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                Intent intent = new Intent(VerifyPhoneNumber.this,Register.class);
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
                Toast.makeText(VerifyPhoneNumber.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                Intent intent = new Intent(VerifyPhoneNumber.this,UserDashboard.class);
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
                Toast.makeText(VerifyPhoneNumber.this, "Registration Unsuccessful !\n"+e.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                Intent intent = new Intent(VerifyPhoneNumber.this,Register.class);
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

                Toast.makeText(VerifyPhoneNumber.this, "Registration Unsuccessful !\n"+e.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                Intent intent = new Intent(VerifyPhoneNumber.this,Register.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

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

                Toast.makeText(VerifyPhoneNumber.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                Intent intent = new Intent(VerifyPhoneNumber.this,OrganisationDetailsForm.class);
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
                Toast.makeText(VerifyPhoneNumber.this, "Registration Unsuccessful !\n"+e.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                Intent intent = new Intent(VerifyPhoneNumber.this,Register.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

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

                Toast.makeText(VerifyPhoneNumber.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                Intent intent = new Intent(VerifyPhoneNumber.this,UserDashboard.class);
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
                Toast.makeText(VerifyPhoneNumber.this, "Registration Unsuccessful !\n"+e.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                Intent intent = new Intent(VerifyPhoneNumber.this,Register.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
    */

}