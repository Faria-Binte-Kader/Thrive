package com.example.thrive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String TAG = "TAG_SIGNUP";
    Button alreadyMember, signUpBtn;
    private EditText inputEmail, inputPassword, confirmPassword;
    private EditText inputName, inputAge, inputHeight, inputWeight;
    private String userId;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signUpBtn = findViewById(R.id.signupButton);
        alreadyMember = findViewById(R.id.alreadyMember);
        inputEmail = findViewById(R.id.signupEmail);
        inputPassword = findViewById(R.id.signupPassword);
        confirmPassword = findViewById(R.id.signupConfirmPassword);

        inputName = findViewById(R.id.signupName);
        inputAge = findViewById(R.id.signupAge);
        inputHeight = findViewById(R.id.signupHeight);
        inputWeight = findViewById(R.id.signupWeight);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                String conPassword = confirmPassword.getText().toString();

                final String name = inputName.getText().toString().toUpperCase();
                final String age = inputAge.getText().toString();
                final String height = inputHeight.getText().toString();
                final String weight = inputWeight.getText().toString();


                if (name.isEmpty() || name.length() < 7) {
                    showError(inputName, "Your Name is not valid");
                    return;
                }

                if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    showError(inputEmail, "Email is not Valid");
                    return;
                }

                if (password.isEmpty() || password.length() < 6) {
                    showError(inputPassword, "Password must be at least 6 characters");
                    return;
                }
                if (conPassword.isEmpty() || !conPassword.equals(password)) {
                    showError(confirmPassword, "Password does not match");
                    return;
                }

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                            fAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Signup.this, "Welcome to Thrive! Please verify your Email Address", Toast.LENGTH_SHORT).show();

                                        userId = fAuth.getCurrentUser().getUid();
                                        DocumentReference documentReference = fStore.collection("User").document(userId);
                                        Map<String, Object> user = new HashMap<>();
                                        user.put("Name", name);
                                        user.put("Email", email);
                                        user.put("Age", age);
                                        user.put("Height", height);
                                        user.put("Weight", weight);
                                        user.put("Coin", "100");
                                        user.put("Description","");
                                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "onSuccess: user profile is created");
                                            }
                                        });
                                        startActivity(new Intent(getApplicationContext(), VerifyEmail.class));
                                    } else {
                                        Toast.makeText(Signup.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(Signup.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        alreadyMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoLoginPage(view);
            }
        });
    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        ((TextView) adapterView.getChildAt(0)).setTextColor(Color.BLACK);
        Toast.makeText(this, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void gotoLoginPage(View view) {
        Intent intent = new Intent(Signup.this, login.class);
        startActivity(intent);
    }
}