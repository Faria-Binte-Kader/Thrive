package com.example.thrive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class VerifyEmail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email);
    }

    public void gotoLoginPage(View view) {
        Intent intent = new Intent(VerifyEmail.this, login.class).putExtra("from", "verifymail");;
        startActivity(intent);
    }
}

