package com.example.alimentoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class RecipeDetail extends AppCompatActivity {

    TextView recipname,prep,duratn,ingre,directn;
    String srecip,sprep,sduratn,singre,sdirectn;
    Button download;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        recipname=findViewById(R.id.tv_recpname);
        prep=findViewById(R.id.tv_prep);
        duratn=findViewById(R.id.tv_duratn);
        ingre=findViewById(R.id.tv_ingr);
        directn=findViewById(R.id.tv_dircts);
        download=findViewById(R.id.button_download);

        db = FirebaseFirestore.getInstance();

        srecip=recipname.getText().toString();
//        sprep=prep.getText().toString();
//        sduratn=duratn.getText().toString();
//        singre=ingre.getText().toString();
//        sdirectn=directn.getText().toString();

        Intent rd = getIntent();
        srecip = rd.getStringExtra("recipename");

        Query query = db.collection("Recipes").whereEqualTo("recipename",srecip);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        recipname.setText(document.getString("recipename"));
                        prep.setText(document.getString("prep"));
                        duratn.setText(document.getString("time"));
                        ingre.setText(document.getString("ing"));
                        directn.setText(document.getString("dir"));
                    }
                }
                else {
                    Toast.makeText(RecipeDetail.this,"Failed",Toast.LENGTH_LONG).show();
                }
            }
        });

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

//        DocumentReference docRef = db.collection("Recipes").document("");
//        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                if (documentSnapshot.exists()){
//                    recipname.setText(documentSnapshot.getString("repname"));
//                    prep.setText(documentSnapshot.getString("prep"));
//                    duratn.setText(documentSnapshot.getString("time"));
//                    ingre.setText(documentSnapshot.getString("ing"));
//                    directn.setText(documentSnapshot.getString("dir"));
//                }
//                else {
//                    Toast.makeText(RecipeDetail.this,"Row not found",Toast.LENGTH_LONG).show();
//                }
//
//            }
//        })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(RecipeDetail.this,e.getMessage(),Toast.LENGTH_LONG).show();
//                    }
//                });

//        db.collection("Recipes")
//                .whereEqualTo("repname",srecip)
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG, document.getId() + " => " + document.getData());
//                            }
//                        } else {
//                            Log.w(TAG, "Error getting documents.", task.getException());
//                        }
//                    }
//                });


    }
}