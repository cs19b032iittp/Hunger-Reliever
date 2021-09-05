package com.charan.hungerreliever;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class HomeOrgFragment extends Fragment {

    private Button searchForDonors;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db1;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_org_home, container, false);
        searchForDonors = (Button) view.findViewById(R.id.search_for_donors);
        firebaseAuth = FirebaseAuth.getInstance();
        db1 = FirebaseFirestore.getInstance();

        searchForDonors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db1.collection("food donations")
                        .whereEqualTo("status",false)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Map<String, Object> donations = document.getData();
                                        //if(String.valueOf(profile.get("user")).equals("1"))
                                        Log.d("TAG", String.valueOf(document.getData()));
                                    }
                                } else {
                                    Log.d("TAG", "Error getting documents: ", task.getException());
                                }
                            }
                        });
            }
        });
        return view;
    }

}
