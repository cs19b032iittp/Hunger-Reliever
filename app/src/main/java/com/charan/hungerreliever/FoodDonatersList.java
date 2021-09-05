package com.charan.hungerreliever;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

public class FoodDonatersList extends AppCompatActivity {


    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ArrayList<FoodDonorDetails> list;
    Adapter adapter;
    private RecyclerViewClickListener listener;

    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donaters_recycler_view);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        initData();


    }

    private void initData() {


        db.collection("food donations")
                .whereEqualTo("status",false)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            list = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> profile = document.getData();
                                list.add(new FoodDonorDetails(String.valueOf(profile.get("email")),
                                        String.valueOf(profile.get("quantity")),
                                        String.valueOf(profile.get("city"))));

                            }
                            initRecyclerView();
                        }
                        else {
                            Toast.makeText(FoodDonatersList.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerViewDonors);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        setOnClickListener();
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new Adapter(list,listener);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void setOnClickListener() {
        listener = new RecyclerViewClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), DonorItemSelect.class);
                Bundle bundle = new Bundle();
                bundle.putString("email",list.get(position).getEmail());
                bundle.putString("quantity",list.get(position).getQuantity());
                bundle.putString("city",list.get(position).getCity());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        };
    }
}
