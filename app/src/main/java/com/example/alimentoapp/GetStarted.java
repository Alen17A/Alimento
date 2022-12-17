package com.example.alimentoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GetStarted extends AppCompatActivity {

    Button glogin,gsignup;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        glogin=findViewById(R.id.button_glogin);
        gsignup=findViewById(R.id.button_gsignup);


        glogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent g = new Intent(GetStarted.this,UserLogin.class);
                startActivity(g);
            }
        });

        gsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent g = new Intent(GetStarted.this,MainActivity.class);
                startActivity(g);
            }
        });
    }
}