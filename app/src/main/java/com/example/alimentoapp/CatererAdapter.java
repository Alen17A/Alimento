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

public class CatererAdapter extends RecyclerView.Adapter<CatererAdapter.MyViewHolder> {

    Context context;
    ArrayList<CatererData> catererDataArrayList;

    public CatererAdapter(Context context, ArrayList<CatererData> catererDataArrayList) {
        this.context = context;
        this.catererDataArrayList = catererDataArrayList;
    }

    @NonNull
    @Override
    public CatererAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.caterer,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CatererAdapter.MyViewHolder holder, int position) {

        CatererData catererData = catererDataArrayList.get(position);

        holder.agencyname.setText(catererData.agencyname);

        holder.catr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent d = new Intent(view.getContext(),CatererDetail.class);
                String agename = catererData.agencyname;
                d.putExtra("agencyname",agename);
                view.getContext().startActivity(d);
            }
        });

    }

    @Override
    public int getItemCount() {
        return catererDataArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView agencyname;
        CardView catr;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            agencyname=itemView.findViewById(R.id.tv_caterers);
            catr=itemView.findViewById(R.id.cardView_catrlst);
        }
    }
}
