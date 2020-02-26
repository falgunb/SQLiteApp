package com.k.sqliteapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.k.sqliteapp.R;

import DBHelper.DBHelper;
import Model.User;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;

public class RegisterActivity extends AppCompatActivity {

    EditText editTextUsername;
    EditText editTextPassword;
    EditText editTextName;

    TextInputLayout textInputName;
    TextInputLayout textInputLayoutUsername;
    TextInputLayout textInputLayoutPassword;
    Button btnRegister;
    DBHelper sqliteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initTextViewLogin();
        initViews();
        sqliteHelper = new DBHelper(this);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    String Name = editTextName.getText().toString();
                    String Username = editTextUsername.getText().toString();
                    String Password = editTextPassword.getText().toString();

                    if (!sqliteHelper.isUserNameExists(Username)) {
                        sqliteHelper.addUser(new User(null, Username, Name, Password));
                        Snackbar.make(btnRegister, "User created successfully! Please Login", Snackbar.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, Snackbar.LENGTH_LONG);
                    } else {
                        Snackbar.make(btnRegister, "User already Exists..", Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    public void initTextViewLogin() {
        TextView textViewLogin = findViewById(R.id.textViewLogin);
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initViews() {
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextUsername = (EditText) findViewById(R.id.editTextUserName);
        textInputName = (TextInputLayout) findViewById(R.id.textInputName);
        textInputLayoutUsername = (TextInputLayout) findViewById(R.id.textInputUserName);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);
        btnRegister = (Button) findViewById(R.id.btnRegister);

    }


    public boolean validate() {
        boolean valid = false;
        String Name = editTextName.getText().toString();
        String Username = editTextUsername.getText().toString();
        String Password = editTextPassword.getText().toString();
        if (Name.isEmpty()) {
            valid = false;
            textInputName.setError("Enter Complete ");
        } else {
            textInputName.setError(null);
        }
        if (Username.isEmpty()) {
            valid = false;
            textInputLayoutUsername.setError("Please enter valid Username");
        } else {
            valid = true;
            textInputLayoutUsername.setError(null);
        }
        if (Password.isEmpty()) {
            valid = false;
            textInputLayoutPassword.setError("Empty password not Allowed");
        } else {
            if (Password.length() > 4) {
                valid = true;
                textInputLayoutPassword.setError(null);
            } else {
                valid = false;
                textInputLayoutPassword.setError("Password Is too short");
            }
        }
        return valid;

    }
}
