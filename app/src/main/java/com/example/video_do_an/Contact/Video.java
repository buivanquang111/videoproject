package com.example.video_do_an.Contact;

import java.io.Serializable;

public class Video implements Serializable {
   private String img;
    private String text;
    private String mp4;

    public Video(){}
    public Video(String img, String text, String mp4) {
        this.img = img;
        this.text = text;
        this.mp4 =mp4;
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

    public String getMp4() {
        return mp4;
    }

    public void setMp4(String mp4) {
        this.mp4 = mp4;
    }
}
