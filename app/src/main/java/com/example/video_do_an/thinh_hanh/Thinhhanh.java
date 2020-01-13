package com.example.video_do_an.thinh_hanh;

import java.io.Serializable;

public class Thinhhanh implements Serializable {
    String thumb;
    String title;

    public Thinhhanh(String thumb, String title) {
        this.thumb = thumb;
        this.title = title;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
