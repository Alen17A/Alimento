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

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.MyViewHolder> {

    Context context;
    ArrayList<RecipeData> recipeDataArrayList;

    public RecipeAdapter(Context context, ArrayList<RecipeData> recipeDataArrayList) {
        this.context = context;
        this.recipeDataArrayList = recipeDataArrayList;
    }

    @NonNull
    @Override
    public RecipeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.recipe,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.MyViewHolder holder, int position) {

        RecipeData recipeData = recipeDataArrayList.get(position);

        holder.recipename.setText(recipeData.recipename);

        holder.recp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent d = new Intent(view.getContext(),RecipeDetail.class);
                String repname = recipeData.recipename;
                d.putExtra("recipename",repname);
                view.getContext().startActivity(d);
            }
        });

    }

    @Override
    public int getItemCount() {
        return recipeDataArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView recipename;
        CardView recp;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            recipename=itemView.findViewById(R.id.tv_recipe);
            recp=itemView.findViewById(R.id.cardView_recplst);



        }
    }
}
