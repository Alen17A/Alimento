package com.example.alimentoapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class RestaurantFoodDetail extends AppCompatActivity {

    RecyclerView recyclerView5;
    ArrayList<FoodDetailData> foodDetailDataArrayList;
    FoodDetailAdapter myadapter5;
    FirebaseFirestore db;
    //StorageReference storageReference;
    ProgressDialog progressDialog;
    TextView hotelnmhd;
    String shotelnmhd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_food_detail);

        hotelnmhd=findViewById(R.id.tv_restfnm);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data....");
        progressDialog.show();

        recyclerView5=findViewById(R.id.recyclerView_fodetail);
        recyclerView5.setHasFixedSize(true);
        recyclerView5.setLayoutManager(new GridLayoutManager(this,2));

        db=FirebaseFirestore.getInstance();
        //storageReference = FirebaseStorage.getInstance().getReference().child("foodimage.jpeg");
        foodDetailDataArrayList = new ArrayList<FoodDetailData>();
        myadapter5 = new FoodDetailAdapter(RestaurantFoodDetail.this,foodDetailDataArrayList);

        recyclerView5.setAdapter(myadapter5);

        shotelnmhd=hotelnmhd.getText().toString();

        Intent rfd = getIntent();
        shotelnmhd = rfd.getStringExtra("hotelname");

        hotelnmhd.setText(shotelnmhd);

        EventChangeListener5();
    }

    private void EventChangeListener5() {

        db.collection("Foods").whereEqualTo("hotelname",shotelnmhd)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error!=null){

                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                            Log.e("Firestore Error",error.getMessage());
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()){

                            if (dc.getType() == DocumentChange.Type.ADDED){

                                foodDetailDataArrayList.add(dc.getDocument().toObject(FoodDetailData.class));
                            }

                            myadapter5.notifyDataSetChanged();
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                        }

                    }
                });

    }
}