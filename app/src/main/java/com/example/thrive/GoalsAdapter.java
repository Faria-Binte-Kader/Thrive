package com.example.thrive;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class GoalsAdapter extends RecyclerView.Adapter<ViewholderGoals> implements AdapterView.OnItemSelectedListener {

    MainActivity mainActivity;
    ArrayList<Goals> goalsArrayList;

    public GoalsAdapter(MainActivity mainActivity, ArrayList<Goals> goalsArrayList) {
        this.mainActivity = mainActivity;
        this.goalsArrayList = goalsArrayList;
    }

    @NonNull
    @Override
    public ViewholderGoals onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(mainActivity.getBaseContext());
        View view = layoutInflater.inflate(R.layout.goal_list, parent, false);
        return new ViewholderGoals(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewholderGoals holder, final int position) {
        holder.progress.setText(goalsArrayList.get(position).getProgress());
        holder.name.setText(goalsArrayList.get(position).getName());

        FirebaseAuth fAuth;
        FirebaseFirestore fStore;

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        holder.updateprogress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String goalID = goalsArrayList.get(position).getGoalID();
                DocumentReference doc = fStore.collection("UserGoalInfo").document(fAuth.getUid()).collection("Goals").document(goalID);
                doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    String cnt;

                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document2 = task.getResult();
                            if (document2.exists()) {
                                cnt = document2.getString("Days");
                                int temp = Integer.parseInt(cnt);
                                temp = temp + 1;
                                cnt = String.valueOf(temp);
                                fStore.collection("UserGoalInfo").document(fAuth.getUid()).collection("Goals").document(goalID)
                                        .update("Days", cnt)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {

                                            }
                                        });
                            }
                        } else {

                        }
                    }
                });

                DocumentReference doc2 = fStore.collection("UserGoalInfo").document(fAuth.getUid()).collection("Goals").document(goalID);
                doc2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    String cnt, duration,progress;

                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document2 = task.getResult();
                            if (document2.exists()) {
                                cnt = document2.getString("Days");
                                duration = document2.getString("Duration");
                                int temp = Integer.parseInt(cnt);
                                temp = temp + 1;
                                int temp2 = Integer.parseInt(duration);
                                int temp3 = (temp * 100) / temp2;
                                progress = String.valueOf(temp3);
                                fStore.collection("UserGoalInfo").document(fAuth.getUid()).collection("Goals").document(goalID)
                                        .update("Progress", progress)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {

                                            }
                                        });
                            }
                        } else {

                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return goalsArrayList.size();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}