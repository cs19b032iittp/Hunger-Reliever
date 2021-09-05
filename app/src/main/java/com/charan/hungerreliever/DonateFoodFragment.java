package com.charan.hungerreliever;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

public class DonateFoodFragment extends Fragment {

    private static final String TAG = "TAG";
    private Button donateFoodBtn;
    private EditText food, adders, quantity, address, city;
    private String food_, adders_, quantity_, address_, city_;
    private FirebaseAuth auth;
    private FirebaseFirestore db1 ,db2;
    private Long donations;

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
        auth = FirebaseAuth.getInstance();
        db1 = FirebaseFirestore.getInstance();
        db2 = FirebaseFirestore.getInstance();

//TODO : get user details to this fragment

        donateFoodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                food_ = food.getText().toString();
                quantity_ = quantity.getText().toString();
                adders_ = address.getText().toString();
                address_= adders.getText().toString();
                city_ = city.getText().toString();

                DocumentReference documentReference =db1.collection("food donations").document(auth.getCurrentUser().getEmail());
                Map<String,Object> data = new HashMap<>();

                data.put("food",food_);
                data.put("quantity",quantity_);
                data.put("adders",adders_);
                data.put("address",address_);
                data.put("city",city_);
                data.put("email",auth.getCurrentUser().getEmail());
                data.put("status",false);

                //org name; org.mobile;



                documentReference.set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getActivity(), "Request Sent", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Please try again later", Toast.LENGTH_SHORT).show();
                        return;
                    }
                });

//                if(TextUtils.isEmpty(food_)){
//                    food.setError("Required.");
//                    return;
//                }
//                if(TextUtils.isEmpty(quantity_)){
//                    quantity.setError("Required.");
//                    return;
//                }
//                if(TextUtils.isEmpty(adders_)){
//                    adders.setError("Required.");
//                    return;
//                }
//                if(TextUtils.isEmpty(address_)){
//                    address.setError("Required.");
//                    return;
//                }
//                if(TextUtils.isEmpty(city_)){
//                    city.setError("Required.");
//                    return;
//                }

//                db1.collection("users")
//                        .whereEqualTo("email",auth.getCurrentUser().getEmail())
//                        .get()
//                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                if (task.isSuccessful()) {
//                                    for (QueryDocumentSnapshot document : task.getResult()) {
//
//
//                                    }
//                                } else {
//                                    Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                                    return;
//                                }
//                            }
//                        });
//
 /*
                Map<String, Object> data2 = new HashMap<>();
                data2.put("name",food_);
                data2.put("quantity",quantity_);
                data2.put("adders",adders_);
                data2.put("address",address_);
                data2.put("city",city_);
                data2.put("status","0");
                db2.collection("food donations").document("LA")
                        .set(data2)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully written!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error writing document", e);
                            }
                        });
*/

                //db1.collection("users").document(auth.getCurrentUser().getUid()).update("foodDonations",donations+1);



//TODO : add database requirement
               // Toast.makeText(getActivity(), food.getText()+" "+quantity.getText()+" "+adders.getText()+" "+address.getText()+" "+city.getText(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}

