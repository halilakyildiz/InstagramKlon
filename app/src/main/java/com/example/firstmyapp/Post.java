package com.example.firstmyapp;

import android.graphics.Bitmap;

public class Post {
    private String user_text;
    private int profile_img;
    private Bitmap post_img;

    public Post(String user_text, int profile_img, Bitmap post_img) {
        this.user_text = user_text;
        this.profile_img = profile_img;
        this.post_img = post_img;
    }

    public String getUser_text() {
        return user_text;
    }

    public void setUser_text(String user_text) {
        this.user_text = user_text;
    }

    public int getProfile_img() {
        return profile_img;
    }

    public void setProfile_img(int profile_img) {
        this.profile_img = profile_img;
    }

    public Bitmap getPost_img() {
        return post_img;
    }

    public void setPost_img(Bitmap post_img) {
        this.post_img = post_img;
    }
}
