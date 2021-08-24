package com.example.thrive;

public class Notification {
    private String userName;
    private String GoalName;
    private String senderName;
    private String senderID;
    private String senderProPicURL;
    private String documentID;

    public Notification() {
    }

    public Notification(String uname, String gnam, String snam, String sid, String senderProPicURL,String documentID) {
        userName=uname;
        GoalName=gnam;
        senderName=snam;
        senderID=sid;
        this.senderProPicURL=senderProPicURL;
        this.documentID=documentID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    public String getGoalName() {
        return GoalName;
    }

    public void setGoalName(String goalName) {
        GoalName = goalName;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getSenderProPicURL() {
        return senderProPicURL;
    }

    public void setSenderProPicURL(String senderProPicURL) {
        this.senderProPicURL = senderProPicURL;
    }
}
