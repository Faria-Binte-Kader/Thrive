package com.example.thrive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class PersonalInfo extends AppCompatActivity {

    public static final String TAG = "TAG_PERSONAL_INFO";

    Button saveBtn;

    private EditText inputName, inputDescription, inputAge,inputHeight, inputWeight;

    private String userId, userEmail;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        saveBtn = findViewById(R.id.saveInfoButton);
        inputName = findViewById(R.id.personalInfoName);
        inputDescription = findViewById(R.id.personalInfoDescription);
        inputAge = findViewById(R.id.personalInfoAge);
        inputHeight = findViewById(R.id.personalInfoHeight);
        inputWeight = findViewById(R.id.personalInfoWeight);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = inputName.getText().toString().toUpperCase();
                final String age = inputAge.getText().toString();
                final String height = inputHeight.getText().toString();
                final String weight = inputWeight.getText().toString();
                final String description = inputDescription.getText().toString();

                if (name.isEmpty() || name.length() < 7) {
                    showError(inputName, "Name must have more than 6 characters");
                    return;
                }

                userId = fAuth.getCurrentUser().getUid();
                userEmail = fAuth.getCurrentUser().getEmail();

                final String email = (String) userEmail;

                DocumentReference documentReference = fStore.collection("User").document(userId);
                Map<String, Object> user = new HashMap<>();
                user.put("Name", name);
                user.put("Email", email);
                user.put("Age", age);
                user.put("Height", height);
                user.put("Weight", weight);
                user.put("Description", description);

                //Log.d(TAG, "Works till documentReference");

                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: user profile is created");
                    }
                });
                Toast.makeText(PersonalInfo.this, "Added Successfully ", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(getApplicationContext(), Profile.class));
                finish();
            }
        });
    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }
}