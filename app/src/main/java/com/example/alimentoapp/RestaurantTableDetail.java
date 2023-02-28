package com.example.alimentoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class RestaurantTableDetail extends AppCompatActivity {

    TextView restnmtb, tmsl1, tmsl2, tmsl3, tmsl4, tmsl5, tmsl6, tmsl7, tmsl8, tmsl9, tmsl10;
    ImageView resttbim;
    CardView tms1,tms2,tms3,tms4,tms5,tms6,tms7,tms8,tms9,tms10;
    FirebaseFirestore db;
    String srestnmtb;
    private int clickCount = 0;
    private final int maxClicks = 5;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_table_detail);

        restnmtb=findViewById(R.id.tv_restnmtb);
        tmsl1=findViewById(R.id.tv_tbsltm1);
        tmsl2=findViewById(R.id.tv_tbsltm2);
        tmsl3=findViewById(R.id.tv_tbsltm3);
        tmsl4=findViewById(R.id.tv_tbsltm4);
        tmsl5=findViewById(R.id.tv_tbsltm5);
        tmsl6=findViewById(R.id.tv_tbsltm6);
        tmsl7=findViewById(R.id.tv_tbsltm7);
        tmsl8=findViewById(R.id.tv_tbsltm8);
        tmsl9=findViewById(R.id.tv_tbsltm9);
        tmsl10=findViewById(R.id.tv_tbsltm10);
        resttbim=findViewById(R.id.im_resttbim);
        tms1=findViewById(R.id.cardView_tm1);
        tms2=findViewById(R.id.cardView_tm2);
        tms3=findViewById(R.id.cardView_tm3);
        tms4=findViewById(R.id.cardView_tm4);
        tms5=findViewById(R.id.cardView_tm5);
        tms6=findViewById(R.id.cardView_tm6);
        tms7=findViewById(R.id.cardView_tm7);
        tms8=findViewById(R.id.cardView_tm8);
        tms9=findViewById(R.id.cardView_tm9);
        tms10=findViewById(R.id.cardView_tm10);

        db = FirebaseFirestore.getInstance();

        srestnmtb = restnmtb.getText().toString();

        Intent rtb = getIntent();
        srestnmtb = rtb.getStringExtra("hotelname3");

        restnmtb.setText(srestnmtb);


        Query query = db.collection("TimeSlot").whereEqualTo("hotelname",srestnmtb);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        if (document != null) {
                            tmsl1.setText(document.getString("timeslot1"));
                            tmsl2.setText(document.getString("timeslot2"));
                            tmsl3.setText(document.getString("timeslot3"));
                            tmsl4.setText(document.getString("timeslot4"));
                            tmsl5.setText(document.getString("timeslot5"));
                            tmsl6.setText(document.getString("timeslot6"));
                            tmsl7.setText(document.getString("timeslot7"));
                            tmsl8.setText(document.getString("timeslot8"));
                            tmsl9.setText(document.getString("timeslot9"));
                            tmsl10.setText(document.getString("timeslot10"));
                        }else {
                            Toast.makeText(RestaurantTableDetail.this,"No Data in Firestore",Toast.LENGTH_LONG).show();
                        }
                    }
                }
                else {
                    Toast.makeText(RestaurantTableDetail.this,"Failed",Toast.LENGTH_LONG).show();
                }
            }
        });

        tms1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(clickCount < maxClicks) {
                    Intent s1 = new Intent(RestaurantTableDetail.this, TableSlotReserve.class);
                    String stmsl1 = tmsl1.getText().toString();
                    s1.putExtra("tmslot1", stmsl1);
                    s1.putExtra("restname", srestnmtb);
                    startActivity(s1);

                    clickCount++;
                }else{
                    Toast.makeText(RestaurantTableDetail.this,"Reservations full",Toast.LENGTH_LONG).show();
                    tms1.setEnabled(false);
                    tms1.setAlpha(0.5f);
                }
            }
        });


    }
}