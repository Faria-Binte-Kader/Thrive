package com.example.thrive;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class AnimalDetails extends AppCompatActivity {

    public static final String TAG = "TAG_ANIMALDETAILS";
    Button BuyBtn;
    ImageView animalbig;
    TextView animaltype,animalprice,animalDetails,heading,usercoin;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private String sp1;
    Integer ucoin,animalcoin;
    String coin,downloadlink;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_details);
        Intent intent = getIntent();
        sp1 = intent.getStringExtra(Shop.EXTRA_TEXT7);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        animalbig=findViewById(R.id.animalbigpic);
        animaltype=findViewById(R.id.animaltype);
        animalprice=findViewById(R.id.buyanimalprice);
        usercoin=findViewById(R.id.cointextview2);
        animalDetails=findViewById(R.id.animaldetails);
        heading=findViewById(R.id.iamanimal);
        BuyBtn=findViewById(R.id.buyanimal);
        heading.setText("I am a "+sp1);
        loadData();
        loadcoininfo();


        BuyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coin= usercoin.getText().toString();
                ucoin=Integer.parseInt(coin);
                coin=animalprice.getText().toString();
                animalcoin=Integer.parseInt(coin);
                if(ucoin-animalcoin>=0)
                {
                    Integer coinint=ucoin-animalcoin;
                    coin=coinint.toString();
                    updatecoin(coin);
                    loadcoininfo();
                    DocumentReference documentReference = fStore.collection("UserAvatarInfo").document();
                    Map<String, Object> user = new HashMap<>();
                    user.put("userID", userID);
                    user.put("DownloadLink", downloadlink);

                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(AnimalDetails.this, "You gained a new avatar!", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else
                {
                    Toast.makeText(AnimalDetails.this, "You do not have enough coins", Toast.LENGTH_LONG).show();
                }
            }});
    }

    private void loadData(){
        fStore.collection("Animals")
                .whereEqualTo("AnimalName", sp1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    String details,price,link,type;

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot querySnapshot : task.getResult()) {
                            link = querySnapshot.getString("DownloadLink");
                            price = querySnapshot.getString("Price");
                            details= querySnapshot.getString("Details");
                            type= querySnapshot.getString("Type");
                        }
                        Picasso.get().load(link).into(animalbig);
                        animalprice.setText(price);
                        animalDetails.setText(details);
                        animaltype.setText(type);
                        downloadlink=link;

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AnimalDetails.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                        Log.v("---I---", e.getMessage());
                    }
                });
    }

    private void loadcoininfo()
    {
        DocumentReference documentReference = fStore.collection("User").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null) {
                    usercoin.setText(value.getString("Coin"));
                }
            }
        });
    }

    private void updatecoin(String s)
    {
        fStore.collection("User").document(userID)
                .update("Coin",s)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AnimalDetails.this, "CONGRATULATIONS", Toast.LENGTH_LONG).show();
                    }
                });
    }
}