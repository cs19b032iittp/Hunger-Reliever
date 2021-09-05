package com.charan.hungerreliever;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;
// Accepted Donations Class
public class AcceptedDonations extends AppCompatActivity {

    private RecyclerView acceptedRV;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<Donations> arrayList;
    private RecyclerViewClickListener listener;
    private FirebaseAuth auth;
    private FirebaseFirestore db,db2;
    private DonationsAdapter adapter2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accepted_donations);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        db2 = FirebaseFirestore.getInstance();

        db.collection("org donations")
                .whereEqualTo("org_email",auth.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            arrayList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> profile = document.getData();
                                //if(String.valueOf(profile.get("org_email")).equals(auth.getCurrentUser().getEmail()))
                                    arrayList.add(new Donations(String.valueOf(profile.get("food_name")),String.valueOf(profile.get("user_email"))));

                            }
                            initRecyclerView();
                        }
                        else {
                            Toast.makeText(AcceptedDonations.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void initRecyclerView() {
        acceptedRV = findViewById(R.id.acceptedRV);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        setOnClickListener();
        acceptedRV.setLayoutManager(linearLayoutManager);
        adapter2 = new DonationsAdapter(arrayList,listener);
        acceptedRV.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();
    }

    private void setOnClickListener() {
        listener = new RecyclerViewClickListener() {
            @Override
            public void onItemClick(View view, int position) {
               Toast.makeText(AcceptedDonations.this, arrayList.get(position).getUser_email(), Toast.LENGTH_SHORT).show();

//                db.collection("org donations")
//                        .whereEqualTo("org_email",auth.getCurrentUser().getEmail())
//                        .get()
//                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                if(task.isSuccessful()){
//                                    arrayList = new ArrayList<>();
//                                    for (QueryDocumentSnapshot document : task.getResult()) {
//                                        Map<String, Object> profile = document.getData();
//                                        //if(String.valueOf(profile.get("org_email")).equals(auth.getCurrentUser().getEmail()))
//                                        arrayList.add(new Donations(String.valueOf(profile.get("food_name")),String.valueOf(profile.get("user_email"))));
//
//                                    }
//                                    initRecyclerView();
//                                }
//                                else {
//                                    Toast.makeText(AcceptedDonations.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//
//                AlertDialog dialog = new AlertDialog.Builder(AcceptedDonations.this)
//                        .setTitle("Donation Details")
//                        .setMessage("Message")
//                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                dialogInterface.dismiss();
//                            }
//                        })
//                        .create();
//
//                dialog.show();


            }
        };
    }


}
