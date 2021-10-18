package com.example.thrive;

public class FriendList {
    private String Name;
    private String ID;

    public FriendList() {
    }

    public FriendList(String nam, String id) {
        Name = nam;
        ID = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String id) {
        ID = id;
    }
}
