package com.example.cinemaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText userEmail;
    EditText userPassword;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userEmail = findViewById(R.id.editTextEmail);
        userPassword = findViewById(R.id.editTextPassword);

        mAuth = FirebaseAuth.getInstance();
    }

    public void login(View view) {

        String email = userEmail.getText().toString();
        String pw = userPassword.getText().toString();

        mAuth.signInWithEmailAndPassword(email,pw).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d(LoginActivity.class.getName(), "Sikeres bejelentkezés!");
                    openMovieList();
                }else{
                    Log.d(LoginActivity.class.getName(), "Sikertelen bejelentlezés!");
                    Toast.makeText(LoginActivity.this, "Sikertelen bejelentkezés: "+ task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    register();
                }
            }
        });

    }

    public void register() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void openMovieList(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}