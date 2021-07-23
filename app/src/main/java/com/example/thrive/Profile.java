package com.example.thrive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Profile extends AppCompatActivity {

    Button updateProfileBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        updateProfileBtn = findViewById(R.id.updatePersonalInfo);
        updateProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoPersonalInfo();
            }
        });
    }

    public void gotoPersonalInfo(){
        Intent intent = new Intent(Profile.this, PersonalInfo.class);
        startActivity(intent);
    }
}