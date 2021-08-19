package com.example.thrive;

public class Goals {
    private  String Progress;
    private String Name;
    private String GoalID;

    public Goals() {
    }

    public Goals(String prog, String nam, String id) {
        Progress = prog;
        Name = nam;
        GoalID = id;
    }

    public String getProgress() {
        return Progress;
    }

    public void setProgress(String progress) {
        Progress = progress;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getGoalID() {
        return GoalID;
    }

    public void setGoalID(String goalID) {
        GoalID = goalID;
    }
}
