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

public class RegisterActivity extends AppCompatActivity {

    EditText userNameET;
    EditText userEmailET;
    EditText userPasswordET;
    EditText userPasswordAgEt;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userNameET = findViewById(R.id.editTextUserName);
        userEmailET = findViewById(R.id.editTextEmail);
        userPasswordET = findViewById(R.id.editTextPassword);
        userPasswordAgEt = findViewById(R.id.editTextPasswordAgain);

        auth = FirebaseAuth.getInstance();
    }

    public void register(View view) {

        String userName = userNameET.getText().toString();
        String userEmail = userEmailET.getText().toString();
        String userPw = userPasswordET.getText().toString();
        String userPwAg = userPasswordAgEt.getText().toString();

        if(!userPw.equals(userPwAg)){
            Toast.makeText(this, "Nem egyeznek a jelszavak!", Toast.LENGTH_LONG).show();
            return;
        }

        auth.createUserWithEmailAndPassword(userEmail, userPw).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d(RegisterActivity.class.getName(),"Felhasználó regisztrálva!");
                    openMovieList();
                }else{
                    Log.d(RegisterActivity.class.getName(), "A felhasználó létrehozás nem sikerült!");
                    Toast.makeText(RegisterActivity.this, "A felhasználó létrehozás nem sikerült!"+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void cancel(View view) {
        finish();
    }
    public void openMovieList(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}