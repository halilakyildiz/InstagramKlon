package com.example.firstmyapp;

import android.media.Image;
import android.widget.ImageView;

public class History {
    private int img;
    private String info;

    public History(int img, String info) {
        this.img = img;
        this.info = info;
    }

    public int getImg() {
        return img;
    }

    public String getInfo() {
        return info;
    }
}
