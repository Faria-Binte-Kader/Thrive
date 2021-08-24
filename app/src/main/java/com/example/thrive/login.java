package com.example.thrive;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.iid.FirebaseInstanceIdReceiver;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String TAG = "TAG_LOGIN";
    EditText loginEmail, loginPassword;
    Button loginBtn, signupBtn, forgotPasswordBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;


    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        if(getIntent().getStringExtra("from")==null){
            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DocumentReference documentReference2 = FirebaseFirestore.getInstance().collection("User").document(user);
                documentReference2.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value != null)
                            startActivity(new Intent(login.this, MainActivity.class));
                    }
                });
                finish();
            }
        }else if(getIntent().getStringExtra("from").equals("verifymail")){

        }

        setContentView(R.layout.activity_login);

        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);
        loginBtn = (Button) findViewById(R.id.loginButton);

        signupBtn = (Button) findViewById(R.id.gotoSignupButton);
        forgotPasswordBtn = (Button) findViewById(R.id.forgotPassword);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = loginEmail.getText().toString();
                String password = loginPassword.getText().toString();

                if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    showError(loginEmail, "Email is not Valid");
                    return;
                }
                if (password.isEmpty()) {
                    showError(loginPassword, "Please enter your password");
                    return;
                }
                if (FirebaseAuth.getInstance() != null && FirebaseFirestore.getInstance() != null) {

                    fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                if (fAuth.getCurrentUser().isEmailVerified()) {
                                    Intent intent = new Intent(login.this, MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                   // String token=returnMeFCMtoken();
                                    finish();
                                } else {
                                    Toast.makeText(login.this, "Please verify your Email Address", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(login.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoSignup(view);
            }
        });


        forgotPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoForgotPassword(view);
            }
        });

    }

    private void gotoForgotPassword(View v) {

        Intent intent = new Intent(login.this, ForgotPassword.class);
        startActivity(intent);

        /*
        EditText resetMail = new EditText(v.getContext());
        AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
        passwordResetDialog.setTitle("Reset Password?");
        passwordResetDialog.setMessage("Enter your email to receive reset link.");
        passwordResetDialog.setView(resetMail);

        passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String mail = resetMail.getText().toString();
                fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(login.this, "Reset Password Link sent to your email.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(login.this, "Error! Reset Link is Not Sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        passwordResetDialog.create().show();
        */
    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }

    public void gotoSignup(View view) {
        Intent intent = new Intent(login.this, Signup.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
        Toast.makeText(this, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    public static String returnMeFCMtoken() {
        final String[] token = {""};
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if(task.isComplete()){
                    token[0] = task.getResult();
                    Log.e("AppConstants", "onComplete: new Token got: "+token[0] );

                }
            }
        });
        return token[0];
    }
}