package com.example.alimentoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RestaurantTableDetailTest extends AppCompatActivity {

    RecyclerView recyclerViewt;
    TableSlotAdapter myadaptert;
    FirebaseFirestore db;
    ProgressDialog progressDialog;
    String srestnmtbt;
    TextView restnmtbt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_table_detail_test);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data....");
        progressDialog.show();

        restnmtbt=findViewById(R.id.tv_restnmtbt);

        recyclerViewt = findViewById(R.id.recyclerView_tbslt);

        Intent tb = getIntent();
        srestnmtbt = tb.getStringExtra("hotelname3");

        restnmtbt.setText(srestnmtbt);

        db = FirebaseFirestore.getInstance();
        DocumentReference documentRef = db.collection("TimeSlot").document(srestnmtbt);

        documentRef.get().addOnSuccessListener(documentSnapshot -> {
            progressDialog.dismiss();
            if (documentSnapshot.exists()) {
                Map<String, Object> data = documentSnapshot.getData();
                if (data != null) {
                    List<String> fields = new ArrayList<>(data.keySet());

                    recyclerViewt.setLayoutManager(new GridLayoutManager(this,2));

                    myadaptert = new TableSlotAdapter(documentRef, fields);
                    recyclerViewt.setAdapter(myadaptert);
                }
            }
            else{
                Toast.makeText(RestaurantTableDetailTest.this, "No Slots", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> {
            progressDialog.dismiss();
            Toast.makeText(RestaurantTableDetailTest.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        });

    }
}