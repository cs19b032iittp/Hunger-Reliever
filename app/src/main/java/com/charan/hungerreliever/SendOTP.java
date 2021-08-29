package com.charan.hungerreliever;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.FileObserver;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
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

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class SendOTP extends AppCompatActivity {

    private Button button;
    private FirebaseAuth auth;
    private  Bundle bundle;
    private ProgressBar progressBar;
    private  String name ,email, phone, password;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_otp);

        button = findViewById(R.id.buttonSendCode);
        auth = FirebaseAuth.getInstance();
        bundle = getIntent().getExtras();
        name = bundle.getString("Name");
        phone = bundle.getString("Phone");
        email = bundle.getString("Email");
        password = bundle.getString("Password");
        progressBar = findViewById(R.id.progressbarSendOTP);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = "+91" + phone;

                progressBar.setVisibility(View.VISIBLE);

                PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(SendOTP.this)
                        .setCallbacks(mCallBacks)
                        .build();

                PhoneAuthProvider.verifyPhoneNumber(options);


            }
        });

        mCallBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                //signIn(phoneAuthCredential);
                progressBar.setVisibility(View.INVISIBLE);
                VerificationCompleted();
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(SendOTP.this, "Verification Failed" + e.getMessage(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SendOTP.this,Register.class));
                finish();
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);


                Toast.makeText(SendOTP.this, "OTP Sent to your number", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(auth.getCurrentUser() == null){
                            Bundle bundle1 =new Bundle();
                            bundle1.putString("Name",name);
                            bundle1.putString("Phone",phone);
                            bundle1.putString("Email",email);
                            bundle1.putString("Password",password);
                            bundle1.putString("auth",s);
                            Intent intent = new Intent(SendOTP.this,VerifyPhoneNumber.class);
                            intent.putExtras(bundle1);
                            startActivity(intent);
                            finish();
                        }

                    }
                },10000);

                progressBar.setVisibility(View.INVISIBLE);




            }
        };

    }

    private void VerificationCompleted() {

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    //userId = firebaseAuth.getUid();

                    /*DocumentReference documentReference = firestore.collection("Users").document(userId);
                    Map<String, Object> user = new HashMap<>();
                    user.put("Name",jName);
                    user.put("Email",jEmail);

                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d(TAG,"onSuccess : User Profile is created" + userId);
                        }
                    });
                    */

                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(SendOTP.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(SendOTP.this, "OTP Verifed! User Signed in", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SendOTP.this,DashBoard.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();

                }
                else {
                    Toast.makeText(SendOTP.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private  void signIn(PhoneAuthCredential credential){
        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SendOTP.this, "OTP Verifed! User Signed in", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SendOTP.this,DashBoard.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(SendOTP.this, "Not Verifed!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}