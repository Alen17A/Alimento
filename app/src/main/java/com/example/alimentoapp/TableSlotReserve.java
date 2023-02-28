package com.example.alimentoapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class TableSlotReserve extends AppCompatActivity {

    TextView restnm, tmslt;
    EditText usnm, usphno, nop, tsdate;
    Button reserve;
    String srestnm, stmslt, susnm, susphno, snop, stsdate;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_slot_reserve);

        restnm=findViewById(R.id.tv_restnmts);
        tmslt=findViewById(R.id.tv_tstm);
        usnm=findViewById(R.id.ed_tsnm);
        usphno=findViewById(R.id.ed_tsphno);
        nop=findViewById(R.id.ed_tsnop);
        tsdate=findViewById(R.id.ed_tsdate);
        reserve=findViewById(R.id.button_tsreserve);

        db = FirebaseFirestore.getInstance();

        Intent tsr = getIntent();
        srestnm = tsr.getStringExtra("restname");
        stmslt = tsr.getStringExtra("tmslot1");

        restnm.setText(srestnm);
        tmslt.setText(stmslt);

        tsdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(TableSlotReserve.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        tsdate.setText(day + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                datePickerDialog.show();
            }
        });

        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    susnm = usnm.getText().toString().trim();
                    susphno = usphno.getText().toString().trim();
                    snop = nop.getText().toString().trim();
                    stsdate = tsdate.getText().toString().trim();

                    if (susnm.isEmpty()) {
                        usnm.setError("This field is required");
                        return;
                    }
                    if (susphno.isEmpty()) {
                        usphno.setError("This field is required");
                        return;
                    }
                    if (snop.isEmpty()) {
                        nop.setError("This field is required");
                        return;
                    }
                    if (stsdate.isEmpty()) {
                        tsdate.setError("This field is required");
                    }

                    saveReserveDatatoFirestore(susnm, susphno, snop, stsdate);
            }

        });
    }

    private void saveReserveDatatoFirestore(String susnm,String susphno,String snop,String stsdate){

        Map<String, Object> tsData = new HashMap<>();
        tsData.put("tsUsername", susnm);
        tsData.put("tsUserphno", susphno);
        tsData.put("tsNoofPeople", snop);
        tsData.put("tsDate", stsdate);
        tsData.put("hotelname", srestnm);
        tsData.put("timeslot", stmslt);

        db.collection("TableReservations").document().set(tsData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        Toast.makeText(TableSlotReserve.this, "Table Reserved Successfully", Toast.LENGTH_SHORT).show();

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