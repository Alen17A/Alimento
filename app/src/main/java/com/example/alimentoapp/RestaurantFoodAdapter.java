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



public class RestaurantFoodAdapter extends RecyclerView.Adapter<RestaurantFoodAdapter.MyViewHolder> {

    Context context;
    ArrayList<RestaurantFoodData> restaurantFoodDataArrayList;

    public RestaurantFoodAdapter(Context context, ArrayList<RestaurantFoodData> restaurantFoodDataArrayList) {
        this.context = context;
        this.restaurantFoodDataArrayList = restaurantFoodDataArrayList;
    }

    @NonNull
    @Override
    public RestaurantFoodAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.restaurant_food,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantFoodAdapter.MyViewHolder holder, int position) {

        RestaurantFoodData restaurantFoodData = restaurantFoodDataArrayList.get(position);

        holder.hotelname.setText(restaurantFoodData.hotelname);
        holder.hoteladdress.setText(restaurantFoodData.hoteladdress);

        holder.rest1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fd1 = new Intent(view.getContext(),RestaurantFoodDetail.class);
                String restname = restaurantFoodData.hotelname;
                fd1.putExtra("hotelname",restname);
                view.getContext().startActivity(fd1);
            }
        });

    }

    @Override
    public int getItemCount() {
        return restaurantFoodDataArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView hotelname,hoteladdress;
        CardView rest1;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            hotelname=itemView.findViewById(R.id.tv_rest1);
            hoteladdress=itemView.findViewById(R.id.tv_rest1ad);
            rest1=itemView.findViewById(R.id.cardView_restlst1);
        }
    }
}
