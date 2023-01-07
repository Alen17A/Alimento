package com.example.alimentoapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RecipePage extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<RecipeData> recipeDataArrayList;
    MyAdapter myAdapter;
    FirebaseFirestore db;
    ProgressDialog progressDialog;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_page);


        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data....");
        progressDialog.show();

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        recipeDataArrayList = new ArrayList<RecipeData>();
        myAdapter = new MyAdapter(RecipePage.this,recipeDataArrayList);

        recyclerView.setAdapter(myAdapter);

        EventChangeListener();

    }

    private void EventChangeListener() {

        db.collection("Recipes").orderBy("recipename", Query.Direction.ASCENDING)
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

                                recipeDataArrayList.add(dc.getDocument().toObject(RecipeData.class));
                            }

                            myAdapter.notifyDataSetChanged();
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                        }

                    }
                });
    }
}