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
import java.util.UUID;

public class CatererBookNow extends AppCompatActivity {

    TextView bkagencnm;
    EditText bkuname,bkuemail,bkuphno,bkuevaddr,bkunop,bkutyf,bkudate,bkutime;
    Button booknow;
    FirebaseFirestore db;
    String sbkagnm,sbkunm,sbkuem,sbkuph,sbkueaddr,sbkunp,sbkutf,sbkudt,sbkutm,userId;

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

                if (sbkunm.isEmpty()) {
                    bkuname.setError("This field is required");
                    return;
                }
                if (sbkuem.isEmpty()) {
                    bkuemail.setError("This field is required");
                    return;
                }
                if (sbkuph.isEmpty()) {
                    bkuphno.setError("This field is required");
                    return;
                }
                if (sbkueaddr.isEmpty()) {
                    bkuevaddr.setError("This field is required");
                    return;
                }
                if (sbkunp.isEmpty()) {
                    bkunop.setError("This field is required");
                    return;
                }
                if (sbkutf.isEmpty()) {
                    bkutyf.setError("This field is required");
                    return;
                }
                if (sbkudt.isEmpty()) {
                    bkudate.setError("This field is required");
                    return;
                }
                if (sbkutm.isEmpty()) {
                    bkutime.setError("This field is required");
                    return;
                }


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

        userId= UUID.randomUUID().toString();

        db.collection("BookingsCaterer").document().set(bkData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        Toast.makeText(CatererBookNow.this, "Booked Successfully", Toast.LENGTH_SHORT).show();
                        Intent b = new Intent(CatererBookNow.this,CatererPage.class);
                        startActivity(b);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }
}