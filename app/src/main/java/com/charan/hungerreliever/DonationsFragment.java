package com.charan.hungerreliever;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DonationsFragment extends Fragment {

    private Button accepted,completed;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_received_donations, container, false);

        View view = inflater.inflate(R.layout.fragment_received_donations,container,false);

        accepted = (Button) view.findViewById(R.id.acceptedDonationsButton);
        completed = (Button) view.findViewById(R.id.completedDonationsButton);


        accepted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;

    }
}
