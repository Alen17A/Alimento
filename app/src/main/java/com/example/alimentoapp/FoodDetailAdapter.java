package com.example.alimentoapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class FoodDetailAdapter extends RecyclerView.Adapter<FoodDetailAdapter.MyViewHolder> {

    Context context;
    ArrayList<FoodDetailData> foodDetailDataArrayList;

    public FoodDetailAdapter(Context context, ArrayList<FoodDetailData> foodDetailDataArrayList) {
        this.context = context;
        this.foodDetailDataArrayList = foodDetailDataArrayList;
    }

    @NonNull
    @Override
    public FoodDetailAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.restaurant_food_detail_card,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodDetailAdapter.MyViewHolder holder, int position) {

        FoodDetailData foodDetailData = foodDetailDataArrayList.get(position);

        holder.foodname.setText(foodDetailData.foodname);
        holder.foodprice.setText(foodDetailData.foodprice);

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("foodimage.jpg");


        Glide.with(holder.itemView.getContext())
                .load(storageReference)
                .placeholder(R.drawable.lock_solid)
                .error(R.drawable.im_error_24)
                .into(holder.foodim);

        holder.fdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fdd = new Intent(view.getContext(),FoodOrderPage.class);
                String fdname = foodDetailData.foodname;
                String htname = foodDetailData.hotelname;
                fdd.putExtra("foodname",fdname);
                fdd.putExtra("hotelname2",htname);
                view.getContext().startActivity(fdd);
            }
        });

    }

    @Override
    public int getItemCount() {
        return foodDetailDataArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView foodname, foodprice;
        ImageView foodim;
        CardView fdd;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            foodname=itemView.findViewById(R.id.tv_restfdnm);
            foodprice=itemView.findViewById(R.id.tv_restfdpr);
            foodim=itemView.findViewById(R.id.im_restfdim);
            fdd=itemView.findViewById(R.id.cardView_fooddetail);
        }
    }
}
