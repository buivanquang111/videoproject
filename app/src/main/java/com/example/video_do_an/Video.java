package com.example.video_do_an;

import java.io.Serializable;

public class Video implements Serializable {
    String img;
    String text;

    public Video(){}
    public Video(String img, String text) {
        this.img = img;
        this.text = text;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
