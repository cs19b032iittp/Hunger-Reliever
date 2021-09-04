package com.charan.hungerreliever;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RemoveAdapter extends  RecyclerView.Adapter<RemoveAdapter.MyViewHolder>{

    ArrayList<RemoveDataModel> dataHolder;
    private RecyclerViewClickListener recyclerViewClickListener;


    public RemoveAdapter(ArrayList<RemoveDataModel> dataHolder, RecyclerViewClickListener recyclerViewClickListener) {
        this.dataHolder = dataHolder;
        this.recyclerViewClickListener = recyclerViewClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_design, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.org.setText(dataHolder.get(position).getOrg());
        holder.upi.setText(dataHolder.get(position).getUpi());
    }

    @Override
    public int getItemCount() {
        return dataHolder.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView org,upi;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            org = itemView.findViewById(R.id.org_upi_list_name);
            upi = itemView.findViewById(R.id.org_upi_list_upi);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            recyclerViewClickListener.onItemClick(view, getAdapterPosition());
        }
    }
}
