package com.example.alimentoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;



public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.MyViewHolder> {

    Context context;
    ArrayList<RestaurantData> restaurantDataArrayList;

    public RestaurantAdapter(Context context, ArrayList<RestaurantData> restaurantDataArrayList) {
        this.context = context;
        this.restaurantDataArrayList = restaurantDataArrayList;
    }

    @NonNull
    @Override
    public RestaurantAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.restaurant,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantAdapter.MyViewHolder holder, int position) {

        RestaurantData restaurantData = restaurantDataArrayList.get(position);

        holder.hotelname.setText(restaurantData.hotelname);

    }

    @Override
    public int getItemCount() {
        return restaurantDataArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView hotelname;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            hotelname=itemView.findViewById(R.id.tv_rest1);
        }
    }
}
