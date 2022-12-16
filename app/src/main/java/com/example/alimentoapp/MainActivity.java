package com.example.alimentoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    EditText name,phno,email,password,cpassword;
    Button signup;
    TextView login;

    FirebaseAuth auth;
    FirebaseFirestore firestore;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name=findViewById(R.id.ed_uname);
        phno=findViewById(R.id.ed_uphno);
        email=findViewById(R.id.ed_uemail);
        password=findViewById(R.id.ed_upassword);
        cpassword=findViewById(R.id.ed_ucpassword);
        signup=findViewById(R.id.button_usignup);
        login=findViewById(R.id.tv_signup);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = name.getText().toString().trim();
                String emailid = email.getText().toString().trim();
                String phoneno = phno.getText().toString().trim();
                String passwd = password.getText().toString().trim();
                String cpasswd = cpassword.getText().toString().trim();

                if (username.isEmpty()) {
                    name.setError("This field is required");
                    return;
                }
                if (emailid.isEmpty()) {
                    email.setError("This field is required");
                    return;
                }
                if (phoneno.isEmpty()) {
                    phno.setError("This field is required");
                    return;
                }
                if (!passwd.equals(cpasswd)) {
                    cpassword.setError("Password does not match");
                    return;
                }

                auth.createUserWithEmailAndPassword(emailid, passwd)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "You are in!!!", Toast.LENGTH_SHORT).show();
                                    UserData obj = new UserData(username, phoneno);
                                    firestore.collection("USERS").document(emailid).set(obj);
                                    Intent i = new Intent(MainActivity.this, UserLogin.class);
                                    startActivity(i);
                                } else {
                                    Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                UserData obj = new UserData(username, phoneno);
                firestore.collection("USERS").document(emailid).set(obj);
            }

        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, UserLogin.class);
                startActivity(i);
            }
        });


    }
}