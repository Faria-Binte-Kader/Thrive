package com.example.thrive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Logout extends AppCompatActivity {

    private Button yes,no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        yes= (Button)findViewById(R.id.yes_btn);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( FirebaseAuth.getInstance()!=null)
                {
                    FirebaseAuth.getInstance().signOut();
                    Toast.makeText(Logout.this, "Logged out Successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Logout.this,login.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();}
            }
        });
        no= (Button)findViewById(R.id.no_btn);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Logout.this, MainActivity.class));
            }
        });
    }
}