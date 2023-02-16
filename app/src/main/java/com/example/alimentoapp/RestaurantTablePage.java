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

public class RestaurantTablePage extends AppCompatActivity {

    RecyclerView recyclerView4;
    ArrayList<RestaurantTableData> restaurantTableDataArrayList;
    RestaurantTableAdapter myAdapter4;
    FirebaseFirestore db;

    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_table_page);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data....");
        progressDialog.show();

        recyclerView4 = findViewById(R.id.recyclerView_rest2);
        recyclerView4.setHasFixedSize(true);
        recyclerView4.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        restaurantTableDataArrayList = new ArrayList<RestaurantTableData>();
        myAdapter4 = new RestaurantTableAdapter(RestaurantTablePage.this, restaurantTableDataArrayList);

        recyclerView4.setAdapter(myAdapter4);

        EventChangeListener4();


    }

    private void EventChangeListener4() {

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

                                restaurantTableDataArrayList.add(dc.getDocument().toObject(RestaurantTableData.class));
                            }

                            myAdapter4.notifyDataSetChanged();
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                        }

                    }
                });
    }
}