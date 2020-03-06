package com.example.video_do_an.define;

import com.example.video_do_an.Contact.Video;

import java.util.ArrayList;

public class Define_Methods {
    public boolean CHECK(String title, ArrayList<Video> arrayList){
        for (Video video:arrayList){
            if(title.equals(video.getText())==true)
                return true;
        }
        return false;
    }
}
