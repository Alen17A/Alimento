package com.example.alimentoapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RestaurantFoodPage extends AppCompatActivity {

    RecyclerView recyclerView3;
    ArrayList<RestaurantFoodData> restaurantFoodDataArrayList;
    RestaurantFoodAdapter myAdapter3;
    FirebaseFirestore db;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_food_page);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data....");
        progressDialog.show();

        recyclerView3 = findViewById(R.id.recyclerView_rest1);
        recyclerView3.setHasFixedSize(true);
        recyclerView3.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        restaurantFoodDataArrayList = new ArrayList<RestaurantFoodData>();
        myAdapter3 = new RestaurantFoodAdapter(RestaurantFoodPage.this, restaurantFoodDataArrayList);

        recyclerView3.setAdapter(myAdapter3);

        EventChangeListener3();


    }

    private void EventChangeListener3() {

        db.collection("Restaurants").orderBy("hotelname", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null){

                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                            Log.e("Firestore Error",error.getMessage());
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()){

                            if (dc.getType() == DocumentChange.Type.ADDED){

                                restaurantFoodDataArrayList.add(dc.getDocument().toObject(RestaurantFoodData.class));
                            }

                            myAdapter3.notifyDataSetChanged();
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                        }

                    }
                });
    }
}