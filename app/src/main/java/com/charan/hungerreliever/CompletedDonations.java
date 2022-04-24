package com.charan.hungerreliever;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;
// CompletedDonations class
public class CompletedDonations extends AppCompatActivity {

    private RecyclerView acceptedRV;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<Donations> arrayList;
    private RecyclerViewClickListener listener;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private DonationsAdapter adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_donations);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        db.collection("food donations")
                .whereEqualTo("status","completed")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            arrayList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> profile = document.getData();
                                if(String.valueOf(profile.get("org_email")).equals(auth.getCurrentUser().getEmail()))
                                    arrayList.add(new Donations(String.valueOf(profile.get("name")),String.valueOf(profile.get("email"))));

                            }
                            initRecyclerView();
                        }
                        else {
                            Toast.makeText(CompletedDonations.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void initRecyclerView() {
        acceptedRV = findViewById(R.id.completedRV);
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
                Toast.makeText(CompletedDonations.this, arrayList.get(position).getUser_email(), Toast.LENGTH_SHORT).show();

            }
        };
    }

}
