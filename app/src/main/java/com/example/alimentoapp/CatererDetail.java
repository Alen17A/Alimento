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
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class CatererDetail extends AppCompatActivity {

    TextView agencname,ownernm,agemail,agephno,ageaddr,agedesc,ageevcap;
    String sagename;
    Button hireus;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caterer_detail);

        agencname=findViewById(R.id.tv_agencyname);
        ownernm=findViewById(R.id.tv_ownernm);
        agemail=findViewById(R.id.tv_agemail);
        agephno=findViewById(R.id.tv_agphno);
        ageaddr=findViewById(R.id.tv_ageaddr);
        agedesc=findViewById(R.id.tv_agedesc);
        ageevcap=findViewById(R.id.tv_ageevcap);
        hireus=findViewById(R.id.button_hireus);

        db = FirebaseFirestore.getInstance();

        sagename=agencname.getText().toString();

        Intent cd = getIntent();
        sagename = cd.getStringExtra("agencyname");

        Query query = db.collection("caterers").whereEqualTo("agencyname",sagename);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        agencname.setText(document.getString("agencyname"));
                        ownernm.setText(document.getString("ownername"));
                        agemail.setText(document.getString("agencyemail"));
                        agephno.setText(document.getString("agencyphno"));
                        ageaddr.setText(document.getString("agencyaddress"));
                        agedesc.setText(document.getString("agencydescription"));
                        ageevcap.setText(document.getString("agencycapacity"));
                    }
                }
                else {
                    Toast.makeText(CatererDetail.this,"Failed",Toast.LENGTH_LONG).show();
                }
            }
        });


        hireus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cb = new Intent(CatererDetail.this,CatererBookNow.class);
                cb.putExtra("agencyname",sagename);
                startActivity(cb);
            }
        });


    }
}