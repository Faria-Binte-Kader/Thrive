package com.example.thrive;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FriendPublicGoalAdapter extends RecyclerView.Adapter<ViewholderFriendPublicGoals> implements AdapterView.OnItemSelectedListener {

    FriendPublicGoal friendPublicGoal;
    ArrayList<GoalsFriend> friendPublicGoalArrayList;


    public FriendPublicGoalAdapter(FriendPublicGoal friendPublicGoal, ArrayList<GoalsFriend> friendPublicGoalArrayList){
        this.friendPublicGoal = friendPublicGoal;
        this.friendPublicGoalArrayList = friendPublicGoalArrayList;
    }


    @NonNull
    @NotNull
    @Override
    public ViewholderFriendPublicGoals onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(friendPublicGoal.getBaseContext());
        View view = layoutInflater.inflate(R.layout.friend_public_goal_list,parent,false);

        return new ViewholderFriendPublicGoals(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewholderFriendPublicGoals holder, int position) {
        holder.category.setText(friendPublicGoalArrayList.get(position).getCategory());
        holder.subcategory.setText(friendPublicGoalArrayList.get(position).getSubcategory());
        holder.goalName.setText(friendPublicGoalArrayList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return friendPublicGoalArrayList.size();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
