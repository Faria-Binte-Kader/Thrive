package com.example.thrive;

public class GoalsFriend {
    private String Name;
    private String GoalID;
    private String UserID;
    private String UserName;
    private String UserProPicURL;

    public GoalsFriend() {
    }

    public GoalsFriend(String goalID, String nam, String userID, String userName, String userProPicURL) {
        Name = nam;
        GoalID = goalID;
        UserID = userID;
        UserName = userName;
        UserProPicURL = userProPicURL;
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

    public String getUserID() { return UserID; }

    public void setUserID(String userID) { UserID = userID; }

    public String getUserName() { return UserName; }

    public void setUserName(String userName) { UserName = userName; }

    public String getUserProPicURL() { return UserProPicURL; }

    public void setUserProPicURL(String userProPicURL) { UserProPicURL = userProPicURL; }
}
