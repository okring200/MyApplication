package com.example.minjena.myapplication.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minjena.myapplication.R;

import com.example.minjena.myapplication.presenter.LoginPresenter;
import com.example.minjena.myapplication.view.ILoginView;

public class LoginActivity extends Activity implements View.OnClickListener, ILoginView {

    EditText etUserName, etPassword;
    Button btnLogin, btnSignup;
    TextView tvData;

    LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvData = (TextView) findViewById(R.id.textView);
        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        btnSignup = findViewById(R.id.btnSignup);
        btnSignup.setOnClickListener(this);

        mLoginPresenter = new LoginPresenter(LoginActivity.this);
    }

    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btnLogin:
                String userName = etUserName.getText().toString();
                String password = etPassword.getText().toString();
                mLoginPresenter.doLogin(userName, password);
                break;
            case R.id.btnSignup:
                Intent intent = new Intent(this, SubmitActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void loginSuccess(String key) {
        Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_LONG).show();
        Bundle arg = new Bundle();
        arg.putString( "key", key);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtras(arg);
        startActivity(intent);
    }

    @Override
    public void loginError() {
        Toast.makeText(getApplicationContext(), "Login Failure", Toast.LENGTH_LONG).show();
    }

}
