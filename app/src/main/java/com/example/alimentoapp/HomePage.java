package com.example.alimentoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomePage extends AppCompatActivity {

    CardView recipes,caterers,food,tableres;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        recipes=findViewById(R.id.cardView_recipes);
        caterers=findViewById(R.id.cardView_caterers);
        food=findViewById(R.id.cardView_food);
        tableres=findViewById(R.id.cardView_tableres);

        recipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent r = new Intent(HomePage.this, RecipePage.class);
                startActivity(r);
            }
        });

        caterers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c = new Intent(HomePage.this, CatererPage.class);
                startActivity(c);
            }
        });

        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent f = new Intent(HomePage.this, RestaurantFoodPage.class);
                startActivity(f );
            }
        });

        tableres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent t = new Intent(HomePage.this, RestaurantTablePage.class);
                startActivity(t);
            }
        });
    }
}