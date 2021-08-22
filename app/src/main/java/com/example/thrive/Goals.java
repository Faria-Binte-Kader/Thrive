package com.example.thrive;

public class Goals {
    private  String Progress;
    private String Name;
    private String GoalID;
    private String Date;
    private String Flag;
    private String Url;

    public Goals() {
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public Goals(String prog, String nam, String id, String date, String flag, String url) {
        Progress = prog;
        Name = nam;
        GoalID = id;
        Date=date;
        Flag=flag;
        Url=url;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getFlag() {
        return Flag;
    }

    public void setFlag(String flag) {
        Flag = flag;
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
