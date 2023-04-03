package com.example.alimentoapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class FoodOrderPage extends AppCompatActivity {

    TextView fddnm, fdddes, fddpr, fddqty;
    ImageView fddim, takeim;
    Button minus, plus, order, take;
    static FirebaseFirestore db;
    FirebaseAuth auth;
    static String sfdnm, sfdpr, sfdqty, usfdnm, usfdph, usfdaddr, emailid, sfdhtnmtk;
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
        auth= FirebaseAuth.getInstance();

        emailid = auth.getCurrentUser().getEmail();

        db.collection("USERS").document(emailid)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            usfdnm = documentSnapshot.getString("username");
                            usfdph = documentSnapshot.getString("phoneno");
                            usfdaddr = documentSnapshot.getString("uaddress");

                        }else {
                            Log.d(TAG, "User data does not exists");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Error getting user data: " + e.getMessage());
                    }
                });


        sfdnm = fddnm.getText().toString();


        Intent fdde = getIntent();
        sfdnm = fdde.getStringExtra("foodname");
        sfdhtnmtk = fdde.getStringExtra("hotelname2");



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
                                    sfdpr = fddpr.getText().toString();
                                    sfdqty = fddqty.getText().toString();

                                }
                            }
                        });

                        plus.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                qty++;
                                fddqty.setText(String.valueOf(qty));
                                fddpr.setText("Rs." + (price * qty));
                                sfdpr = fddpr.getText().toString();
                                sfdqty = fddqty.getText().toString();
                            }
                        });
                    }
                } else {
                    Toast.makeText(FoodOrderPage.this, "Failed", Toast.LENGTH_LONG).show();
                }
            }
        });


        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FoodOrderDialog foodOrderDialog = new FoodOrderDialog();
                foodOrderDialog.show(getSupportFragmentManager(), "FoodOrderDialog");
            }
        });


        take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveTakeawayDatatoFirestore(usfdnm, usfdph);

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


    private void saveTakeawayDatatoFirestore(String usfdnm, String usfdph){

        Map<String, Object> tkData = new HashMap<>();
        tkData.put("foodname", sfdnm);
        tkData.put("foodprice", sfdpr);
        tkData.put("foodquantity", sfdqty);
        tkData.put("tkusname", usfdnm);
        tkData.put("tkusphno", usfdph);
        tkData.put("hotelname", sfdhtnmtk);

        db.collection("FoodTakeaways").document().set(tkData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        Toast.makeText(FoodOrderPage.this, "Takeaway ordered successfully", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }


    public static class FoodOrderDialog extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater layoutInflater = getLayoutInflater();
            View foodorderview = layoutInflater.inflate(R.layout.food_order_dialog,null);
            builder.setView(foodorderview);

            Button orforme = foodorderview.findViewById(R.id.button_orforme);
            Button orforothers = foodorderview.findViewById(R.id.button_orforothers);
            orforme.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveOrderForMeDatatoFirestore(usfdnm,usfdph,usfdaddr);
                    dismiss();
                }
            });

            orforothers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent fdor = new Intent(getActivity(),OrderFood.class);
                    fdor.putExtra("foodname",sfdnm);
                    fdor.putExtra("foodprice",sfdpr);
                    fdor.putExtra("foodquantity",sfdqty);
                    fdor.putExtra("hotelname", sfdhtnmtk);
                    startActivity(fdor);
                }
            });
            return builder.create();

        }
    }


    public static void saveOrderForMeDatatoFirestore(String usfdnm, String usfdph, String usfdaddr){

        Map<String, Object> ormData = new HashMap<>();
        ormData.put("foodname", sfdnm);
        ormData.put("foodprice", sfdpr);
        ormData.put("foodquantity", sfdqty);
        ormData.put("orusname", usfdnm);
        ormData.put("orusphno", usfdph);
        ormData.put("orusaddr", usfdaddr);
        ormData.put("hotelname", sfdhtnmtk);

        db.collection("FoodOrders").document().set(ormData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
//                        Toast.makeText(FoodOrderPage.this, "Ordered successfully", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

}