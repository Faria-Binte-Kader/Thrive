package com.example.thrive;

public class ModelReminder {
    private String ReminderName;
    private String Type;
    private String Duration;
    private String date;
    private String documentID;
    private String intentID;

    public ModelReminder() {
    }

    public String getIntentID() {
        return intentID;
    }

    public void setIntentID(String intentID) {
        this.intentID = intentID;
    }

    public ModelReminder(String rname, String type, String duration, String date, String documentID, String intentid) {
       ReminderName=rname;
       Type=type;
       Duration=duration;
       this.date=date;
       this.documentID=documentID;
       intentID=intentid;
    }

    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    public String getReminderName() {
        return ReminderName;
    }

    public void setReminderName(String reminderName) {
        ReminderName = reminderName;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
