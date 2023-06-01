package com.devedidayg.drinkapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.devedidayg.drinkapp.Database.Database;

public class LoginActivity extends AppCompatActivity {

    EditText edtUsername, edtPassword;
    Button btn_login;
    TextView textViewRegNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textViewRegNav = findViewById(R.id.textView_reg_nav);
        edtUsername = findViewById(R.id.editText_username);
        edtPassword = findViewById(R.id.editText_password);
        btn_login = findViewById(R.id.but_login);

        String first = "New User ";
        String next = "<font color='#03A9F4'>Register Here</font>";
        textViewRegNav.setText(Html.fromHtml(first + next));

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                Database db = new Database(getApplicationContext(), "drinkDatabase",null,1);
                if (username.length()==0 || password.length()==0){
                    Toast.makeText(LoginActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }else {
                    if (db.login(username,password)>0){
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username",username);
                        //to save our data with key and value.
                        editor.apply();
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this, "User does not exist", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        textViewRegNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));
            }
        });
    }
}