package com.stucom.thearchive.modelo_feed;

import com.google.gson.annotations.SerializedName;

public class Feed {

    @SerializedName("username")
    private String username;
    @SerializedName("type")
    private int type;
    @SerializedName("date")
    private String date;


    public Feed(String username, int type, String date) {
        this.username = username;
        this.type = type;
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
