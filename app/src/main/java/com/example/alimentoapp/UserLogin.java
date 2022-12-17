package com.example.alimentoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserLogin extends AppCompatActivity {

    EditText loemail, lopasswd;
    Button uslogin;
    TextView signup;

    FirebaseAuth auth;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        loemail=findViewById(R.id.ed_lemail);
        lopasswd=findViewById(R.id.ed_lpasswd);
        uslogin=findViewById(R.id.button_login);
        signup=findViewById(R.id.tv_signup);


        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        uslogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String logemail=loemail.getText().toString().trim();
                String logpasswd=lopasswd.getText().toString().trim();

                if (logemail.isEmpty()) {
                    loemail.setError("Email is required");
                    loemail.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(logemail).matches()){
                    loemail.setError("Please enter a valid email address");
                    loemail.requestFocus();
                    return;
                }

                if (logpasswd.isEmpty()) {
                    lopasswd.setError("Password is required");
                    lopasswd.requestFocus();
                    return;
                }

                if (logpasswd.length() < 6){
                    lopasswd.setError("Minimum password length is 6 characters!");
                    lopasswd.requestFocus();
                    return;
                }


                auth.signInWithEmailAndPassword(logemail, logpasswd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Intent h = new Intent(UserLogin.this, HomePage.class);
                            startActivity(h);
                            Toast.makeText(UserLogin.this,"Logged in successfully", Toast.LENGTH_LONG).show();
                        } else
                        {
                            Toast.makeText(UserLogin.this, "Invalid Credentials", Toast.LENGTH_LONG).show();
                        }

                    }
                });

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserLogin.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
}