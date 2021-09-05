package com.charan.hungerreliever;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
// Donations Adapter Class
public class DonationsAdapter extends RecyclerView.Adapter<DonationsAdapter.ViewHolder>{


    private ArrayList<Donations> list;
    private RecyclerViewClickListener listener;

    public DonationsAdapter(ArrayList<Donations> list, RecyclerViewClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String Name = list.get(position).getFood_name();
        String address = list.get(position).getUser_email();

        holder.setData(Name,address);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView Name, address;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Name = itemView.findViewById(R.id.FoodName);
            address = itemView.findViewById(R.id.userEmail);
            itemView.setOnClickListener(this);

        }

        public void setData(String Name,  String address) {
            this.Name.setText(Name);
            this.address.setText(address);
        }

        @Override
        public void onClick(View view) {
            listener.onItemClick(view, getAdapterPosition());
        }
    }
}


//package com.charan.hungerreliever;
//
//import android.view.View;
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.ViewGroup;
//import android.widget.TextView;
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//import java.util.ArrayList;
//import java.util.List;
//
//public class DonationsAdapter extends RecyclerView.Adapter<DonationsAdapter.Viewholder> {
//
//    private RecyclerViewClickListener listener;
//    private ArrayList<Donations> arrayList;
//
//    // Constructor
//    public DonationsAdapter(Context context, ArrayList<Donations> arrayList) {
//        this.listener = listener;
//        this.arrayList = arrayList;
//    }
//
//    @NonNull
//    @Override
//    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        // to inflate the layout for each item of recycler view.
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
//        return new Viewholder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull Viewholder holder, int position, @NonNull List<Object> payloads) {
//        super.onBindViewHolder(holder, position, payloads);
//        String foodname = arrayList.get(position).getFood_name();
//        String email = arrayList.get(position).getUser_email();
//
//        holder.set
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull DonationsAdapter.Viewholder holder, int position) {
//        // to set data to textview and imageview of each card layout
//        Donations model = arrayList.get(position);
//        holder.foodName.setText(model.getFood_name());
//        holder.useremai.setText(model.getUser_email());
//    }
//
//    @Override
//    public int getItemCount() {
//        // this method is used for showing number
//        // of card items in recycler view.
//        return arrayList.size();
//    }
//
//    // View holder class for initializing of
//    // your views such as TextView and Imageview.
//    public class Viewholder extends RecyclerView.ViewHolder {
//        private TextView foodName, useremai;
//
//        public Viewholder(@NonNull View itemView) {
//            super(itemView);
//            foodName = itemView.findViewById(R.id.FoodName);
//            useremai = itemView.findViewById(R.id.userEmail);
//            itemView.setOnClickListener(this);
//        }
//
//        @Override
//        public void onClick(View view) {
//            listener.onItemClick(view, getAdapterPosition());
//        }
//    }
//}
