package com.audbar.odre.loycards;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText etUserName = (EditText) findViewById(R.id.userNameText);
        final EditText etEmail = (EditText) findViewById(R.id.emailText);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final EditText etRetypePassword = (EditText) findViewById(R.id.retypePasswordText);

        final Button bRegister = (Button) findViewById(R.id.registerButton);
    }
}