package com.charan.hungerreliever;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class DashBoard extends AppCompatActivity {

    private Button button;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        button = findViewById(R.id.buttonDashboard);
        firebaseAuth = FirebaseAuth.getInstance();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(firebaseAuth.getCurrentUser() != null){
                    FirebaseAuth.getInstance().signOut();
                    button.setText("Login");
                }
                else{
                    startActivity(new Intent(new Intent(getApplicationContext(),Login.class)));
                    finish();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(firebaseAuth.getCurrentUser() != null){
            button.setText("LogOut");
        }
        else{
            button.setText("Login");
        }
    }
}