package com.charan.hungerreliever;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
// Adapter
public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{


    private ArrayList<FoodDonorDetails> list;
    private RecyclerViewClickListener listener;

    public Adapter(ArrayList<FoodDonorDetails> list, RecyclerViewClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.donor_item_design, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String donorName = list.get(position).getEmail();
        String address = list.get(position).getQuantity();
        String mobile = list.get(position).getCity();

        holder.setData(donorName,mobile,address);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView donorName, address, mobile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            donorName = itemView.findViewById(R.id.donor_rec_name);
            address = itemView.findViewById(R.id.donor_rec_address);
            mobile = itemView.findViewById(R.id.donor_rec_mobile);
            itemView.setOnClickListener(this);

        }

        public void setData(String donorName, String mobile, String address) {
            this.donorName.setText(donorName);
            this.mobile.setText(mobile);
            this.address.setText(address);
        }

        @Override
        public void onClick(View view) {
            listener.onItemClick(view, getAdapterPosition());
        }
    }
}
