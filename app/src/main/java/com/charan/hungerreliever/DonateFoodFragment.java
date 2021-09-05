package com.charan.hungerreliever;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DonateFoodFragment extends Fragment {

    private Button donateFoodBtn;
    private EditText food, adders, quantity, address, city;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_donate_food, container, false);
        donateFoodBtn = view.findViewById(R.id.donate_food_btn);
        food = view.findViewById(R.id.food_name);
        quantity = view.findViewById(R.id.food_quantity);
        adders = view.findViewById(R.id.food_adders);
        address = view.findViewById(R.id.user_current_address);
        city = view.findViewById(R.id.user_city);

//TODO : get user details to this fragment

        donateFoodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//TODO : add database requirement
                Toast.makeText(getActivity(), food.getText()+" "+quantity.getText()+" "+adders.getText()+" "+address.getText()+" "+city.getText(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}

