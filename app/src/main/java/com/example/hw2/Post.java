package com.example.hw2;

import com.google.gson.annotations.SerializedName;

public class Post {
    private int user_id;
    private int id;
    private String title;

    @SerializedName("body")
    private String text;

    public int getUser_id() {
        return user_id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }
}
