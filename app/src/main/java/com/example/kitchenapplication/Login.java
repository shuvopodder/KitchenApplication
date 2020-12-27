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

public class Login extends AppCompatActivity implements View.OnClickListener {

    private TextView reg;
    private EditText e,p;
    private Button sn;
    private ProgressBar pb;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        reg = findViewById(R.id.log_register);
        reg.setOnClickListener(this);

        e=findViewById(R.id.log_email);
        p=findViewById(R.id.log_password);
        pb =findViewById(R.id.log_progressBar);

        sn = findViewById(R.id.log_signin);
        sn.setOnClickListener(this);

        mAuth =FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.log_register:
                startActivity(new Intent(this,Register.class));
                break;
            case R.id.log_signin:
                userLogin();
                break;
        }
    }

    private void userLogin() {
        String em = e.getText().toString().trim();
        String ps = p.getText().toString().trim();

        if(em.isEmpty())
        {
            e.setError("Email is required!");
            e.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(em).matches()){
            e.setError("Please provide a valid email");
            e.requestFocus();
            return;
        }
        if(ps.isEmpty()){
            p.setError("Password is required!");
            p.requestFocus();
            return;
        }
        if(ps.length()<6)
        {
            p.setError("Password should be more than 6 letter");
            p.requestFocus();
            return;
        }
        pb.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(em,ps).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(Login.this,MainActivity.class));
                    pb.setVisibility(View.GONE);
                }else{
                    Toast.makeText(Login.this,"Failed to login!please Check your credentitials",Toast.LENGTH_LONG).show();
                    pb.setVisibility(View.GONE);
                }
            }
        });

    }
}