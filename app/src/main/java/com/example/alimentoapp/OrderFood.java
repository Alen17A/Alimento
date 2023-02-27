package com.example.alimentoapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class OrderFood extends AppCompatActivity {

    TextView fdorfdnm, fdorfdpr, fdorfdqty;
    EditText fdornm, fdorph, fdorad;
    Button fdorder;
    ImageView fdorim;
    FirebaseFirestore db;
    static String sfdorfdnm;
    String sfdorfdpr;
    String sfdorfdqty;
    String sfdornm;
    String sfdorph;
    String sfdorad;
    String sfdorht;

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
        sfdorht = or.getStringExtra("hotelname");



        fdorfdnm.setText(sfdorfdnm);
        fdorfdpr.setText(sfdorfdpr);
        fdorfdqty.setText(sfdorfdqty);


        fdorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sfdornm = fdornm.getText().toString().trim();
                sfdorph = fdorph.getText().toString().trim();
                sfdorad = fdorad.getText().toString().trim();

                if (sfdornm.isEmpty()){
                    fdornm.setError("This field is required");
                    return;
                }
                if (sfdorph.isEmpty()){
                    fdorph.setError("This field is required");
                    return;
                }
                if (sfdorad.isEmpty()){
                    fdorad.setError("This field is required");
                    return;
                }

                saveOrderDatatoFirestore(sfdornm,sfdorph,sfdorad);


            }
        });
    }

    private void saveOrderDatatoFirestore(String sfdornm, String sfdorph, String sfdorad){

        Map<String, Object> orData = new HashMap<>();
        orData.put("orUsername", sfdornm);
        orData.put("orUserphno", sfdorph);
        orData.put("orUseraddress", sfdorad);
        orData.put("foodname", sfdorfdnm);
        orData.put("foodprice", sfdorfdpr);
        orData.put("foodquantity", sfdorfdqty);
        orData.put("hotelname", sfdorht);

        db.collection("FoodOrders").document().set(orData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        OrderFood.OrderDialog orderDialog = new OrderDialog();
                        orderDialog.show(getSupportFragmentManager(), "OrderDialog");
                        Toast.makeText(OrderFood.this, "Ordered Successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

    public static class OrderDialog extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater layoutInflater = getLayoutInflater();
            View orfddiaview = layoutInflater.inflate(R.layout.order_dialog, null);
            builder.setView(orfddiaview);

            Query query = FirebaseFirestore.getInstance().collection("Foods").whereEqualTo("foodname", sfdorfdnm);
            query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                        String sordiafdtm = documentSnapshot.getString("foodtime");

                        TextView ordiafdtm = orfddiaview.findViewById(R.id.tv_fdordiatime);
                        ordiafdtm.setText(sordiafdtm);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("OrderDialog", "Error retrieving food time: " + e.getMessage());
                }
            });

            Button ok = orfddiaview.findViewById(R.id.button_orderdiaok);
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