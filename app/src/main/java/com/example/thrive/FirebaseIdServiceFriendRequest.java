package com.example.thrive;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

import org.jetbrains.annotations.NotNull;

public class FirebaseIdServiceFriendRequest extends FirebaseMessagingService {

    private String TAG = "TAG FirebaseIDServiceFriendRequest";

    @Override
    public void onNewToken(String s)
    {
        super.onNewToken(s);
        FirebaseFirestore fStore = FirebaseFirestore.getInstance();
        String userID = FirebaseAuth.getInstance().getUid();

        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(token -> {
            if (!TextUtils.isEmpty(token)) {
                fStore.collection("User").document(userID).update("Token", token);
                Log.d(TAG, "YOUR token is " + token);
            } else {
                Log.w(TAG, "token should not be null...");
            }
        }).addOnFailureListener(e -> {

        }).addOnCanceledListener(() -> {

        }).addOnCompleteListener(task -> Log.v(TAG, "This is the token : " + task.getResult()));

    }
}
