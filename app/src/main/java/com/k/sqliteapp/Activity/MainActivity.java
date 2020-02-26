package com.k.sqliteapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.k.sqliteapp.R;

import DBHelper.DBHelper;
import Model.User;

public class MainActivity extends AppCompatActivity {
    EditText editTextUsername;
    EditText editTextPassword;

    TextInputLayout textInputLayoutUsername;
    TextInputLayout textInputLayoutPassword;
    public Context context;
    Button btnLogin;
    DBHelper sqlitehelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sqlitehelper = new DBHelper(this);
        initCreateTextView();
        initView();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    String Username = editTextUsername.getText().toString();
                    String Password = editTextPassword.getText().toString();
                    User curruntUser = sqlitehelper.Authenticate(new User(null, Username, null, Password));

                    if (curruntUser != null) {
                        Snackbar.make(btnLogin, "Login Success!", Snackbar.LENGTH_SHORT).show();
                        /* Intent intent=new Intent(LoginActivity.this,HomeScreenActivity.class);
                        startActivity(intent);
                        finish();*/
                    } else {
                        Snackbar.make(btnLogin, "Error Logging In", Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void initCreateTextView() {
        TextView textViewCreateAccount = findViewById(R.id.textViewCreateAccount);
        textViewCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        editTextUsername = findViewById(R.id.editTextUserName);
        editTextPassword = findViewById(R.id.editTextPassword);
        textInputLayoutUsername = findViewById(R.id.textInputUserName);
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword);
        btnLogin = findViewById(R.id.btnLogin);

    }

    public boolean validate() {
        boolean valid = false;

        String UserName = editTextUsername.getText().toString();
        String Password = editTextPassword.getText().toString();

        if (TextUtils.isEmpty(UserName)) {
            Toast.makeText(context, "Enter Username..", Toast.LENGTH_LONG).show();
        } else {
            valid = true;
            textInputLayoutUsername.setError(null);
        }
        if (Password.isEmpty()) {
            valid = false;
            textInputLayoutPassword.setError("Please Enter Password!");
        } else {
            if (Password.length() > 4) {
                valid = true;
                textInputLayoutPassword.setError(null);
            } else {
                valid = false;
                textInputLayoutPassword.setError("You only need to set password greater then 2.. why so lazyness?");
            }
        }

        return valid;
    }

}

