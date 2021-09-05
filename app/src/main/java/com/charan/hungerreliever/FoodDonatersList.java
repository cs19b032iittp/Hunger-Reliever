package com.charan.hungerreliever;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FoodDonatersList extends AppCompatActivity {


    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ArrayList<FoodDonorDetails> list;
    Adapter adapter;
    private RecyclerViewClickListener listener;
//opens a recycler view of donors and respective food item
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donaters_recycler_view);


        initData();
        initRecyclerView();


    }

    private void initData() {
//the lists contain name,mobile number and address
        list = new ArrayList<>();
        list.add(new FoodDonorDetails("Name", "0000000000","address"));
        list.add(new FoodDonorDetails("Name", "0000000000","address"));
        list.add(new FoodDonorDetails("Name", "0000000000","address"));
        list.add(new FoodDonorDetails("Name", "0000000000","address"));
        list.add(new FoodDonorDetails("Name", "0000000000","address"));
        list.add(new FoodDonorDetails("Name", "0000000000","address"));
        list.add(new FoodDonorDetails("Name", "0000000000","address"));
        list.add(new FoodDonorDetails("Name", "0000000000","address"));
        list.add(new FoodDonorDetails("Name", "0000000000","address"));
        list.add(new FoodDonorDetails("Name", "0000000000","address"));
        list.add(new FoodDonorDetails("Name", "0000000000","address"));
        list.add(new FoodDonorDetails("Name", "0000000000","address"));
        list.add(new FoodDonorDetails("Name", "0000000000","address"));
        list.add(new FoodDonorDetails("Name", "0000000000","address"));
        list.add(new FoodDonorDetails("Name", "0000000000","address"));
        list.add(new FoodDonorDetails("Name", "0000000000","address"));
        list.add(new FoodDonorDetails("Name", "0000000000","address"));
        list.add(new FoodDonorDetails("Name", "0000000000","address"));
        list.add(new FoodDonorDetails("Name", "0000000000","address"));
        list.add(new FoodDonorDetails("Name", "0000000000","address"));
        list.add(new FoodDonorDetails("Name", "0000000000","address"));
        list.add(new FoodDonorDetails("Name", "0000000000","address"));
        list.add(new FoodDonorDetails("Name", "0000000000","address"));
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
                bundle.putString("name",list.get(position).getDonor());
                bundle.putString("mobile",list.get(position).getMobile());
                bundle.putString("address",list.get(position).getAddress());
//TODO : also keep food details in the bundle
                intent.putExtras(bundle);
                //after clicking on respective item details of the owner is shown
                startActivity(intent);
            }
        };
    }
}
