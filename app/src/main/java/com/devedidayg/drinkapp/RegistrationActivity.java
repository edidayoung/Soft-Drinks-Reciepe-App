package com.devedidayg.drinkapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.devedidayg.drinkapp.Database.Database;

public class RegistrationActivity extends AppCompatActivity {

    EditText etUsername, etPassword, eMail, etPasswordConfirm;
    Button btn_reg;
    TextView textViewLogNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        textViewLogNav = findViewById(R.id.textView_log_nav);
        etUsername = findViewById(R.id.editText_username_reg);
        etPassword = findViewById(R.id.editText_password_reg);
        eMail = findViewById(R.id.editText_email);
        etPasswordConfirm = findViewById(R.id.editText_confirm_password);
        btn_reg = findViewById(R.id.but_reg);

        String first = "Already have an account? ";
        String next = "<font color='#03A9F4'>LOGIN</font>";
        textViewLogNav.setText(Html.fromHtml(first + next));

        if (isDeviceRegistered()) {
            startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            finish();
            return;
        }



        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String edtPasswordConfirm = etPasswordConfirm.getText().toString();
                String email = eMail.getText().toString();
                Database db = new Database(getApplicationContext(), "drinkDatabase", null, 1);
                if (username.length() == 0 || password.length() == 0 || email.length() == 0) {
                    Toast.makeText(RegistrationActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else if (!isValidEmail(email)) {
                    Toast.makeText(RegistrationActivity.this, "Invalid email address", Toast.LENGTH_SHORT).show();
                } else {
                    if (password.compareTo(edtPasswordConfirm) == 0) {
                        if (isValid(password)) {
                            db.register(username, email, password);
                            Toast.makeText(RegistrationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                            finish();
                        } else {
                            Toast.makeText(RegistrationActivity.this, "Passwords must contain at least 8 characters with digits, letters, and special characters.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegistrationActivity.this, "Passwords do not match. Try again", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        textViewLogNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        });
    }

    private boolean isDeviceRegistered() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String registrationFlag = sharedPreferences.getString("registration_flag", null);
        return registrationFlag != null;
    }
    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    public static boolean isValid(String passwordHere) {
        int f1 = 0, f2 = 0, f3 = 0;
        if (passwordHere.length() < 8) {
            return false;
        } else {
            for (int p = 0; p < passwordHere.length(); p++) {
                if (Character.isLetter(passwordHere.charAt(p))) {
                    f1 = 1;
                }
            }
            for (int r = 0; r < passwordHere.length(); r++) {
                if (Character.isDigit(passwordHere.charAt(r))) {
                    f2 = 1;
                }
            }
            for (int s = 0; s < passwordHere.length(); s++) {
                char c = passwordHere.charAt(s);
                if (c >= 33 && c <= 46 || c == 64) {
                    f3 = 1;
                }
            }
            if (f1==1 && f2==1 && f3 ==1)
                return true;
            return false;
        }
    }
}