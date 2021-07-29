package com.example.thrive;

public class Avatar {
    private String  userId;
    private String downloadlink;

    public Avatar(){}

    public Avatar(String uid, String dl)
    {
        userId=uid;
        downloadlink=dl;
    }

    public String getUserId() {
        return userId;
    }
    public String getDownloadlink() {
        return downloadlink;
    }
    public void setDownloadlink(String link) {
        downloadlink= link;
    }
    public void setuserid(String id) {
        userId = id;
    }
}


