package com.charan.hungerreliever;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class CompletedDonations extends AppCompatActivity {

    private RecyclerView completedRV;

    private ArrayList<Donations> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_donations);
        completedRV = findViewById(R.id.completedRV);

        arrayList = new ArrayList<>();
        arrayList.add(new Donations("Name1","email1"));
        arrayList.add(new Donations("Name2","email2"));
        arrayList.add(new Donations("Name3","email3"));
        arrayList.add(new Donations("Name4","email4"));

        CompletdDonationsAdapter adapter= new CompletdDonationsAdapter(this, arrayList);

        // below line is for setting a layout manager for our recycler view.
        // here we are creating vertical list so we will provide orientation as vertical
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        completedRV.setLayoutManager(linearLayoutManager);
        completedRV.setAdapter(adapter);
    }
}