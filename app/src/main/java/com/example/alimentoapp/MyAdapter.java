package com.example.alimentoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<RecipeData> recipeDataArrayList;

    public MyAdapter(Context context, ArrayList<RecipeData> recipeDataArrayList) {
        this.context = context;
        this.recipeDataArrayList = recipeDataArrayList;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.recipe,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {

        RecipeData recipeData = recipeDataArrayList.get(position);

        holder.recipename.setText(recipeData.recipename);

    }

    @Override
    public int getItemCount() {
        return recipeDataArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView recipename;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            recipename=itemView.findViewById(R.id.tv_recipe);
        }
    }
}
