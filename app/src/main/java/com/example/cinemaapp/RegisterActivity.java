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
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    EditText userNameET;
    EditText userEmailET;
    EditText userPasswordET;
    EditText userPasswordAgEt;

    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userNameET = findViewById(R.id.editTextUserName);
        userEmailET = findViewById(R.id.editTextEmail);
        userPasswordET = findViewById(R.id.editTextPassword);
        userPasswordAgEt = findViewById(R.id.editTextPasswordAgain);

        auth = FirebaseAuth.getInstance();

        user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){
            Log.d(AddNewFoglalas.class.getName(), "Auth user!");
            finish();
        }
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
                    Log.d(RegisterActivity.class.getName(),"Felhaszn??l?? regisztr??lva!");
                    openMovieList();
                }else{
                    Log.d(RegisterActivity.class.getName(), "A felhaszn??l?? l??trehoz??s nem siker??lt!");
                    Toast.makeText(RegisterActivity.this, "A felhaszn??l?? l??trehoz??s nem siker??lt!"+task.getException().getMessage(), Toast.LENGTH_LONG).show();
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