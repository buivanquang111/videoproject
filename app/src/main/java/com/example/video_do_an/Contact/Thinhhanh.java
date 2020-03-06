package com.example.video_do_an.Contact;

import java.io.Serializable;

public class Thinhhanh implements Serializable {
    String avatar;
    String title;
    String filemp4;

    public Thinhhanh(String avatar, String title, String filemp4) {
        this.avatar = avatar;
        this.title = title;
        this.filemp4 = filemp4;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFilemp4() {
        return filemp4;
    }

    public void setFilemp4(String filemp4) {
        this.filemp4 = filemp4;
    }
}
