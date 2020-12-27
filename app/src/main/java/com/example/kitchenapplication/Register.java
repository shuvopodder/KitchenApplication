package com.example.kitchenapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity implements View.OnClickListener{

    private Button registerUser;
    private TextView log_page;
    private EditText name,email,phone,password;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth =FirebaseAuth.getInstance();

        registerUser = findViewById(R.id.signup);
        registerUser.setOnClickListener(this);

        name = findViewById(R.id.edit_name);
        email = findViewById(R.id.edit_email);
        phone = findViewById(R.id.edit_phone);
        password = findViewById(R.id.edit_password);

        log_page=findViewById(R.id.log);

        progressBar = findViewById(R.id.progressBar2);

        log_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this,Login.class));
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.signup:
                registerUser();
                break;
            case R.id.log:
                startActivity(new Intent(this,Login.class));
        }
    }

    private void registerUser() {
        String em = email.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String phn = phone.getText().toString().trim();
        String nm = name.getText().toString().trim();

        if(nm.isEmpty()){
            name.setError("Full name is required!");
            name.requestFocus();
            return;
        }
        if(pass.isEmpty()){
            password.setError("Password is required!");
            password.requestFocus();
            return;
        }
        if(em.isEmpty()){
            email.setError("Email is required!");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(em).matches()){
            email.setError("Please provide a valid email");
            email.requestFocus();
            return;
        }
        if(pass.length()<6)
        {
            password.setError("Password should be more than 6 letter");
            password.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(em,pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(nm,em,phn);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(Register.this,"User has been registered successfully",Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                        startActivity(new Intent(Register.this,Sucessfull_Register.class ));
                                    }else{
                                        Toast.makeText(Register.this,"Failed register!try again!",Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(Register.this,"Failed to register! try again!",Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);

                        }
                    }
                });
    }
}