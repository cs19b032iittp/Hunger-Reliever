package com.charan.hungerreliever;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyPhoneNumber extends AppCompatActivity {

    private Button verify_Button;
    private EditText otp;
    private String verificationCodeBySystem;
    private  FirebaseAuth auth;
    private Bundle bundle;
    private  String name ,email, phone, password;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone_number);

        verify_Button = findViewById(R.id.buttonVerify);
        otp = findViewById(R.id.otp);
        bundle = getIntent().getExtras();
        name = bundle.getString("Name");
        phone = bundle.getString("Phone");
        email = bundle.getString("Email");
        password = bundle.getString("Password");
        verificationCodeBySystem = bundle.getString("auth");
        progressBar = findViewById(R.id.progressBarVerify);
        auth = FirebaseAuth.getInstance();

        verify_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String verification_code = otp.getText().toString();
                if(!verification_code.isEmpty()){
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCodeBySystem,verification_code);
                    signIn(credential);
                }
                else{
                    Toast.makeText(VerifyPhoneNumber.this, "Please Enter OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });

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
                    //Toast.makeText(VerifyPhoneNumber.this, "Account", Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(VerifyPhoneNumber.this,DashBoard.class));
                    //finish();
                }
                else{
                    Toast.makeText(VerifyPhoneNumber.this, "User Not Verified!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void VerificationCompleted() {

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(VerifyPhoneNumber.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(SendOTP.this, "OTP Verifed! User Signed in", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(VerifyPhoneNumber.this,DashBoard.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(VerifyPhoneNumber.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

}