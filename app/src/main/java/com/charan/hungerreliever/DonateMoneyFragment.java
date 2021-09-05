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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

public class DonateMoneyFragment extends Fragment implements RecyclerViewClickListener{


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerViewClickListener recyclerViewClickListener;
    RecyclerView recyclerView;
    ArrayList<RemoveDataModel> list;
    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;

    public DonateMoneyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DataFragment.
     */

    // TODO: Rename and change types and number of parameters
    public static DonateFoodFragment newInstance(String param1, String param2){
        DonateFoodFragment fragment = new DonateFoodFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        if(getArguments()!=null){
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_donate_money, container, false);
        recyclerView = view.findViewById(R.id.org_upi_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

//        list.add(new RemoveDataModel("org2","org2@okicici"));
//        list.add(new RemoveDataModel("org3","org3@okaxis"));
//        list.add(new RemoveDataModel("org4","org4@okgs"));
//        list.add(new RemoveDataModel("org5","org5@okbank"));
//        list.add(new RemoveDataModel("org6","org6@oklvb"));
//        list.add(new RemoveDataModel("org7","org7@oksrb"));
//        list.add(new RemoveDataModel("org8","org8@oksb"));
//        list.add(new RemoveDataModel("org9","org9@okrbi"));
//        list.add(new RemoveDataModel("org10","org10@oksbi"));
//        list.add(new RemoveDataModel("org11","org11@oksbi"));
//        list.add(new RemoveDataModel("org12","org12@okicici"));
//        list.add(new RemoveDataModel("org13","org13@okaxis"));
//        list.add(new RemoveDataModel("org14","org14@okgs"));
//        list.add(new RemoveDataModel("org15","org15@okbank"));
//        list.add(new RemoveDataModel("org16","org16@oklvb"));
//        list.add(new RemoveDataModel("org17","org17@oksrb"));
//        list.add(new RemoveDataModel("org18","org18@oksb"));
//        list.add(new RemoveDataModel("org19","org19@okrbi"));
//        list.add(new RemoveDataModel("org20","org20@oksbi"));


        db = FirebaseFirestore.getInstance();

        db.collection("verifyOrganisations")
                .whereEqualTo("verificationStatus","1")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            list = new ArrayList<>();
                            list.add(new RemoveDataModel("org1","org1@oksbi"));
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> profile = document.getData();
                                list.add(new RemoveDataModel(String.valueOf(profile.get("name")),String.valueOf(profile.get("upi_id"))));
                            }

                            setOnClickListener();
                            recyclerView.setAdapter(new RemoveAdapter(list, DonateMoneyFragment.this));

                        } else {
                            Toast.makeText(getActivity(), "please try again later", Toast.LENGTH_SHORT).show();
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }

                    private void add(String name, String upi_id) {
                        list.add(new RemoveDataModel( name,upi_id ));
                    }
                });

        return view;
    }

    public void setOnClickListener(){
        recyclerViewClickListener = new RecyclerViewClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), UpiPayment.class);
                Bundle bundle = new Bundle();
                String org = list.get(position).getOrg();
                String upi = list.get(position).getUpi();
                Toast.makeText(getActivity(), org+" "+upi ,Toast.LENGTH_SHORT).show();
                bundle.putString("org",list.get(position).getOrg());
                bundle.putString("upi",list.get(position).getUpi());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        };
    }


    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getActivity(), UpiPayment.class);
        Bundle bundle = new Bundle();
        String org = list.get(position).getOrg();
        String upi = list.get(position).getUpi();
        Toast.makeText(getActivity(), org+" "+upi ,Toast.LENGTH_SHORT).show();
        bundle.putString("org",list.get(position).getOrg());
        bundle.putString("upi",list.get(position).getUpi());
        intent.putExtras(bundle);
        startActivity(intent);
    }
}

