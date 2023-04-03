package com.example.alimentoapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
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

        StorageReference storageReference = FirebaseStorage.getInstance().getReference("images/"+".jpg");

        try {
            File localfile = File.createTempFile("tempfile",".jpg");
            storageReference.getFile(localfile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                            Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                            holder.foodim.setImageBitmap(bitmap);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context.getApplicationContext(), "Failed to retrieve",Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


//        Glide.with(holder.foodim.getContext())
//                .load(storageReference)
//                .placeholder(R.drawable.lock_solid)
//                .error(R.drawable.im_error_24)
//                .into(holder.foodim);

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

        String imid;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            foodname=itemView.findViewById(R.id.tv_restfdnm);
            foodprice=itemView.findViewById(R.id.tv_restfdpr);
            foodim=itemView.findViewById(R.id.im_restfdim);
            fdd=itemView.findViewById(R.id.cardView_fooddetail);
        }
    }
}
