package com.example.thrive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class PersonalInfo extends AppCompatActivity {

    public static final String TAG = "TAG_PERSONAL_INFO";

    Button saveBtn;

    private EditText inputName, inputOldPassword, inputNewPassword, confirmPassword, inputDescription, inputAge, inputHeight, inputWeight;

    private FirebaseUser fUser;
    private String userID;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        saveBtn = findViewById(R.id.saveInfoButton);
        inputName = findViewById(R.id.personalInfoName);
        inputOldPassword = findViewById(R.id.personalInfoOldPassword);
        inputNewPassword = findViewById(R.id.personalInfoPassword);
        confirmPassword = findViewById(R.id.personalInfoConfirmPassword);
        inputDescription = findViewById(R.id.personalInfoDescription);
        inputAge = findViewById(R.id.personalInfoAge);
        inputHeight = findViewById(R.id.personalInfoHeight);
        inputWeight = findViewById(R.id.personalInfoWeight);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userID = fAuth.getCurrentUser().getUid();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePersonalInfo();
            }
        });
    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }

    private void updatePersonalInfo() {
        final String name = inputName.getText().toString().toUpperCase();
        final String oldPassword = inputOldPassword.getText().toString();
        final String newPassword = inputNewPassword.getText().toString();
        String conPassword = confirmPassword.getText().toString();
        final String age = inputAge.getText().toString();
        final String height = inputHeight.getText().toString();
        final String weight = inputWeight.getText().toString();
        final String description = inputDescription.getText().toString();

        if (!name.isEmpty() && name.length() < 7) {
            showError(inputName, "Name must have more than 6 characters");
            return;
        }

        if (oldPassword.isEmpty() && !newPassword.isEmpty()) {
            showError(inputOldPassword, "Please input old password to change password");
            return;
        }

        if (!newPassword.isEmpty() && newPassword.length() < 6) {
            showError(inputNewPassword, "Password must be at least 6 characters");
            return;
        }
        if ((conPassword.isEmpty() || !conPassword.equals(newPassword)) && !newPassword.isEmpty()) {
            showError(confirmPassword, "Password does not match");
            return;
        }



        if (!oldPassword.isEmpty() && !conPassword.isEmpty()) {
            fUser = FirebaseAuth.getInstance().getCurrentUser();
            String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
            AuthCredential credential = EmailAuthProvider.getCredential(email, oldPassword);

            fUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        fUser.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                View coordinatorLayout;
                                if (!task.isSuccessful()) {
                                    Log.d(TAG, "Error in update password");
                                } else {
                                    Toast.makeText(PersonalInfo.this, "Password Changed ", Toast.LENGTH_SHORT).show();
                                    inputOldPassword.setText("");
                                    inputNewPassword.setText("");
                                    confirmPassword.setText("");
                                }
                            }
                        });
                    } else {
                        Toast.makeText(PersonalInfo.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

        if (!name.isEmpty()) {
            fStore.collection("User").document(userID)
                    .update("Name", name.toUpperCase())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(PersonalInfo.this, "Updated Name", Toast.LENGTH_SHORT).show();
                            inputName.setText("");
                        }
                    });
        }

        if (!age.isEmpty()) {
            fStore.collection("User").document(userID)
                    .update("Age", age)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(PersonalInfo.this, "Updated Age", Toast.LENGTH_SHORT).show();
                            inputAge.setText("");
                        }
                    });
        }

        if (!height.isEmpty()) {
            fStore.collection("User").document(userID)
                    .update("Height", height)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(PersonalInfo.this, "Updated Height", Toast.LENGTH_SHORT).show();
                            inputHeight.setText("");
                        }
                    });
        }

        if (!weight.isEmpty()) {
            fStore.collection("User").document(userID)
                    .update("Weight", weight)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(PersonalInfo.this, "Updated Weight", Toast.LENGTH_SHORT).show();
                            inputWeight.setText("");
                        }
                    });
        }

        if (!description.isEmpty()) {
            fStore.collection("User").document(userID)
                    .update("Description", description)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(PersonalInfo.this, "Updated Description", Toast.LENGTH_SHORT).show();
                            inputDescription.setText("");
                        }
                    });
        }

        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}