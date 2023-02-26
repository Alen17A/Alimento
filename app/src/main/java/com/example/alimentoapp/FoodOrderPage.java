package com.example.alimentoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class FoodOrderPage extends AppCompatActivity {

    TextView fddnm, fdddes, fddpr, fddqty;
    ImageView fddim, takeim;
    Button minus, plus, order, take;
    FirebaseFirestore db;
    String sfdnm, sfdpr;
    int qty = 1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_order_page);

        fddnm = findViewById(R.id.tv_fddnm);
        fdddes = findViewById(R.id.tv_fdddes);
        fddpr = findViewById(R.id.tv_fddpr);
        fddqty = findViewById(R.id.tv_fddqty);
        fddim = findViewById(R.id.im_fddim);
        takeim = findViewById(R.id.im_takeawim);
        minus = findViewById(R.id.button_minus);
        plus = findViewById(R.id.button_plus);
        order = findViewById(R.id.button_orderfood);
        take = findViewById(R.id.button_takeaway);

        db = FirebaseFirestore.getInstance();

        sfdpr = fddpr.getText().toString();
        sfdnm = fddnm.getText().toString();

        Intent fdde = getIntent();
        sfdnm = fdde.getStringExtra("foodname");



        Query query = db.collection("Foods").whereEqualTo("foodname", sfdnm);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        fddnm.setText(document.getString("foodname"));
                        fdddes.setText(document.getString("foodescription"));
                        sfdpr = document.getString("foodprice");
                        double price = Double.parseDouble(sfdpr);
                        fddpr.setText((String.valueOf(Math.round(price))));

                        minus.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (qty > 1) {
                                    qty--;
                                    fddqty.setText(String.valueOf(qty));
                                    fddpr.setText("Rs." + (price * qty));
                                }
                            }
                        });

                        plus.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                qty++;
                                fddqty.setText(String.valueOf(qty));
                                fddpr.setText("Rs." + (price * qty));
                            }
                        });
                    }
                } else {
                    Toast.makeText(FoodOrderPage.this, "Failed", Toast.LENGTH_LONG).show();
                }
            }
        });


        take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TakeawayDialog takeawayDialog = new TakeawayDialog();
                takeawayDialog.show(getSupportFragmentManager(), "TakeawayDialog");
            }
        });

    }

    public static class TakeawayDialog extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater layoutInflater = getLayoutInflater();
            View takeawview = layoutInflater.inflate(R.layout.takeaway_dialog,null);
            builder.setView(takeawview);

            Button ok = takeawview.findViewById(R.id.button_takeawok);
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
             return builder.create();

        }
    }

}