package com.example.alimentoapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class UserLogin extends AppCompatActivity {

    EditText loemail, lopasswd;
    Button uslogin;
    TextView signup;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    String logemail,logpasswd;

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


//        loemail.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                logemail = s.toString().trim();
//                if (!TextUtils.isEmpty(logemail) && !Patterns.EMAIL_ADDRESS.matcher(logemail).matches()) {
//                    DocumentReference docRef = firestore.collection("USERS").document(logemail);
//                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                            if (task.isSuccessful()){
//                                DocumentSnapshot document = task.getResult();
//                                if (!document.exists()){
//                                    Toast.makeText(getApplicationContext(),"Email does not exixts",Toast.LENGTH_LONG).show();
////                                    Intent ul = new Intent(UserLogin.this,MainActivity.class);
////                                    startActivity(ul);
//                                }
//                            }else {
//                                Log.d(TAG,"Error getting documents: ", task.getException());
//                            }
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });


        uslogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                logemail=loemail.getText().toString().trim();
                logpasswd=lopasswd.getText().toString().trim();

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

//    private void checkEmailExists(String logemail) {
//
//        DocumentReference docRef = firestore.collection("USERS").document(logemail);
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()){
//                    DocumentSnapshot document = task.getResult();
//                    if (!document.exists()){
//                        Toast.makeText(getApplicationContext(),"Email does not exixts",Toast.LENGTH_LONG).show();
//                        Intent ul = new Intent(UserLogin.this,MainActivity.class);
//                        startActivity(ul);
//                    }
//                }else {
//                    Log.d(TAG,"Error getting documents: ", task.getException());
//                }
//            }
//        });
//    }
}