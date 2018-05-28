package com.example.minjena.myapplication.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.minjena.myapplication.R;
import com.example.minjena.myapplication.presenter.SubmitPresenter;
import com.example.minjena.myapplication.view.ISubmitView;

import java.util.ArrayList;

public class SubmitActivity extends Activity implements ISubmitView, View.OnClickListener {

    EditText etNewId,etNewPasswd,etNewName,etNewPhone,etNewEmail;
    Button btnSubmit;

    SubmitPresenter mSubmitPresenter;

    protected  void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        etNewId = findViewById(R.id.etNewId);
        etNewPasswd = findViewById(R.id.etNewPasswd);
        etNewName = findViewById(R.id.etNewName);
        etNewPhone = findViewById(R.id.etNewPhone);
        etNewEmail = findViewById(R.id.etNewEmail);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

        mSubmitPresenter = new SubmitPresenter(SubmitActivity.this);
    }

    public void onClick(View view) {
        ArrayList<String> list = new ArrayList<String>();
        list.add(etNewId.getText().toString());
        list.add(etNewPasswd.getText().toString());
        list.add(etNewName.getText().toString());
        list.add(etNewPhone.getText().toString());
        list.add(etNewEmail.getText().toString());
        mSubmitPresenter.validation(list);
    }

    @Override
    public void validFailed() {
        Toast.makeText(getApplicationContext(), "Submit Again", Toast.LENGTH_LONG).show();
    }

    @Override
    public void submitSuccess() {
        Toast.makeText(getApplicationContext(), "Submit Success", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void submitError() {
        Toast.makeText(getApplicationContext(), "Submit Failed", Toast.LENGTH_LONG).show();
    }
}
