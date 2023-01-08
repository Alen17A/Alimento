package com.example.alimentoapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class CatererBookNow extends AppCompatActivity {

    TextView bkagencnm;
    EditText bkuname,bkuemail,bkuphno,bkuevaddr,bkunop,bkutyf,bkudate,bkutime;
    Button booknow;
    FirebaseFirestore db;
    String sbkagnm,sbkunm,sbkuem,sbkuph,sbkueaddr,sbkunp,sbkutf,sbkudt,sbkutm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caterer_book_now);

        bkagencnm=findViewById(R.id.tv_bkagencnm);
        bkuname=findViewById(R.id.ed_bkunm);
        bkuemail=findViewById(R.id.ed_bkuemail);
        bkuphno=findViewById(R.id.ed_bkuphno);
        bkuevaddr=findViewById(R.id.ed_bkuevaddr);
        bkunop=findViewById(R.id.ed_bkunop);
        bkutyf=findViewById(R.id.ed_bkutyf);
        bkudate=findViewById(R.id.ed_bkudate);
        bkutime=findViewById(R.id.ed_bkutime);
        booknow=findViewById(R.id.button_booknow);

        db=FirebaseFirestore.getInstance();

        sbkagnm=bkagencnm.getText().toString();


        Intent bk=getIntent();
        sbkagnm=bk.getStringExtra("agencyname");

        Query query = db.collection("caterers").whereEqualTo("agencyname",sbkagnm);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        bkagencnm.setText(document.getString("agencyname"));
                    }
                }
                else {
                    Toast.makeText(CatererBookNow.this,"Failed",Toast.LENGTH_LONG).show();
                }
            }
        });




        booknow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sbkunm=bkuname.getText().toString().trim();
                sbkuem=bkuemail.getText().toString().trim();
                sbkuph=bkuphno.getText().toString().trim();
                sbkueaddr=bkuevaddr.getText().toString().trim();
                sbkunp=bkunop.getText().toString().trim();
                sbkutf=bkutyf.getText().toString().trim();
                sbkudt=bkudate.getText().toString().trim();
                sbkutm=bkutime.getText().toString().trim();


                saveDatatoFirestore(sbkagnm,sbkunm,sbkuem,sbkuph,sbkueaddr,sbkunp,sbkutf,sbkudt,sbkutm);

            }
        });


    }

    private void saveDatatoFirestore(String sbkagnm, String sbkunm, String sbkuem, String sbkuph, String sbkueaddr, String sbkunp, String sbkutf, String sbkudt, String sbkutm) {

        Map<String, Object> bkData = new HashMap<>();
        bkData.put("bkagencyname",sbkagnm);
        bkData.put("bkfullname", sbkunm);
        bkData.put("bkemail", sbkuem);
        bkData.put("bkphno", sbkuph);
        bkData.put("bkeventaddress", sbkueaddr);
        bkData.put("bknoofpeople", sbkunp);
        bkData.put("bktypeoffood", sbkutf);
        bkData.put("bkdate", sbkudt);
        bkData.put("bktime", sbkutm);

        db.collection("BookingsCaterer").add(bkData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        Toast.makeText(CatererBookNow.this,"Booked Successfully",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }
}