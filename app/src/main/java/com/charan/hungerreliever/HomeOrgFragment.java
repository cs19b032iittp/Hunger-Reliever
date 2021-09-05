package com.charan.hungerreliever;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeOrgFragment extends Fragment {
//when button was clicked
    private Button searchForDonors;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_org_home, container, false);
        searchForDonors = (Button) view.findViewById(R.id.search_for_donors);
        searchForDonors.setOnClickListener(new View.OnClickListener() {
            //open new activity
            @Override
            public void onClick(View view) {
                openFoodDonatersListActivity();
            }
        });
        return view;
    }

    public void openFoodDonatersListActivity(){
        Intent intent = new Intent(getActivity(),FoodDonatersList.class);
        startActivity(intent);
    }
}


