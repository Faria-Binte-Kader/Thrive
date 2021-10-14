package com.example.thrive;

import java.util.List;

public class Statistics {
    private String Name;
    private String GoalID;
    private List<String> MonthStat;

    public Statistics(){

    }

    public Statistics(String goalID, String name, List<String> monthStat) {
        Name = name;
        GoalID = goalID;
        MonthStat = monthStat;
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

    public List<String> getMonthStat() {
        return MonthStat;
    }

    public void setMonthStat(List<String> monthStat) {
        MonthStat = monthStat;
    }

}
