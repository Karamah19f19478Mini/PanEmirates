package com.example.panemirates;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class Adapter extends RecyclerView.Adapter<MyViewHolder> {

    private Context context;
    private List<dataClass> dataList;

    public Adapter(Context context, List<dataClass> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_home_page, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(dataList.get(position).getDataImage19f19478()).into(holder.recImage19f19478);
        holder.recProductName.setText(dataList.get(position).getProducName19f19478());
        holder.recDesc19f19478.setText(dataList.get(position).getDataDesc19f19478());
        holder.recPrice.setText(dataList.get(position).getPrice());

        holder.recCard19F19478.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent19f19478 = new Intent(context, ActivityDetail.class);
                intent19f19478.putExtra("Image", dataList.get(holder.getAdapterPosition()).getDataImage19f19478());
                intent19f19478.putExtra("Description", dataList.get(holder.getAdapterPosition()).getDataDesc19f19478());
                intent19f19478.putExtra("Product Name", dataList.get(holder.getAdapterPosition()).getProducName19f19478());
                intent19f19478.putExtra("Key",dataList.get(holder.getAdapterPosition()).getKey19f19478());
                intent19f19478.putExtra("Price", dataList.get(holder.getAdapterPosition()).getPrice());
                context.startActivity(intent19f19478);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void searchDataList(ArrayList<dataClass> searchList){
        dataList = searchList;
        notifyDataSetChanged();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder{

    ImageView recImage19f19478;
    TextView recProductName, recDesc19f19478, recPrice;
    CardView recCard19F19478;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        recImage19f19478 = itemView.findViewById(R.id.recImage19f19478);
        recCard19F19478 = itemView.findViewById(R.id.recCard19f19478);
        recDesc19f19478 = itemView.findViewById(R.id.recDesc19f19478);
        recPrice = itemView.findViewById(R.id.recPrice);
        recProductName = itemView.findViewById(R.id.recProductName);
    }
}