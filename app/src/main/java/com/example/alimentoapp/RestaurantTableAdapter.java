package com.example.alimentoapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RestaurantTableAdapter extends RecyclerView.Adapter<RestaurantTableAdapter.MyViewHolder> {

    Context context;
    ArrayList<RestaurantTableData> restaurantTableDataArrayList;

    public RestaurantTableAdapter(Context context, ArrayList<RestaurantTableData> restaurantTableDataArrayList) {
        this.context = context;
        this.restaurantTableDataArrayList = restaurantTableDataArrayList;
    }

    @NonNull
    @Override
    public RestaurantTableAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.restaurant_table,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantTableAdapter.MyViewHolder holder, int position) {

        RestaurantTableData restaurantTableData = restaurantTableDataArrayList.get(position);

        holder.hotelname.setText(restaurantTableData.hotelname);
        holder.hoteladdress.setText(restaurantTableData.hoteladdress);

        holder.rest2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tb = new Intent(view.getContext(),RestaurantTableDetail.class);
                view.getContext().startActivity(tb);
            }
        });

    }

    @Override
    public int getItemCount() {
        return restaurantTableDataArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView hotelname, hoteladdress;
        CardView rest2;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            hotelname = itemView.findViewById(R.id.tv_rest2);
            hoteladdress = itemView.findViewById(R.id.tv_rest2ad);
            rest2 = itemView.findViewById(R.id.cardView_restlst2);
        }
    }
}
