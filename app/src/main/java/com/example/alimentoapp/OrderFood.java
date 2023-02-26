package com.example.alimentoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

public class OrderFood extends AppCompatActivity {

    TextView fdorfdnm, fdorfdpr, fdorfdqty;
    EditText fdornm, fdorph, fdorad;
    Button fdorder;
    ImageView fdorim;
    FirebaseFirestore db;
    String sfdorfdnm, sfdorfdpr, sfdorfdqty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_food);

        fdorfdnm = findViewById(R.id.tv_fdorfdnm);
        fdorfdpr = findViewById(R.id.tv_fdorfdpr);
        fdorfdqty = findViewById(R.id.tv_fdorfdqty);
        fdornm = findViewById(R.id.ed_fdornm);
        fdorph = findViewById(R.id.ed_fdorph);
        fdorad = findViewById(R.id.ed_fdorad);
        fdorder = findViewById(R.id.button_fdorder);
        fdorim = findViewById(R.id.im_fdorim);

        db = FirebaseFirestore.getInstance();

        Intent or = getIntent();
        sfdorfdnm = or.getStringExtra("foodname");
        sfdorfdpr = or.getStringExtra("foodprice");
        sfdorfdqty = or.getStringExtra("foodquantity");

        fdorfdnm.setText(sfdorfdnm);
        fdorfdpr.setText(sfdorfdpr);
        fdorfdqty.setText(sfdorfdqty);
    }
}