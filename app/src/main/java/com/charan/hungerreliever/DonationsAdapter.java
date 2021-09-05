package com.charan.hungerreliever;

import android.view.View;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class DonationsAdapter extends RecyclerView.Adapter<DonationsAdapter.Viewholder> {

    private Context context;
    private ArrayList<Donations> arrayList;

    // Constructor
    public DonationsAdapter(Context context, ArrayList<Donations> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public DonationsAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonationsAdapter.Viewholder holder, int position) {
        // to set data to textview and imageview of each card layout
        Donations model = arrayList.get(position);
        holder.foodName.setText(model.getFood_name());
        holder.useremai.setText(model.getUser_email());
    }

    @Override
    public int getItemCount() {
        // this method is used for showing number
        // of card items in recycler view.
        return arrayList.size();
    }

    // View holder class for initializing of
    // your views such as TextView and Imageview.
    public class Viewholder extends RecyclerView.ViewHolder {
        private TextView foodName, useremai;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.FoodName);
            useremai = itemView.findViewById(R.id.userEmail);
        }
    }
}
