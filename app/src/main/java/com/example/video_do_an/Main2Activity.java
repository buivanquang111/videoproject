package com.example.video_do_an;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.video_do_an.databinding.ActivityMain2Binding;

public class Main2Activity extends AppCompatActivity {
    String link_mp4;
    String title;
    MediaController mediaController;
    ActivityMain2Binding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main2);
        VideoView videoView= findViewById(R.id.playVideo);
        link_mp4= getIntent().getStringExtra("link_mp4");
        title=getIntent().getStringExtra("title_mp4");

        binding.tvtitleplayvideo.setText(title);

        mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        Uri video = Uri.parse(link_mp4);
        videoView.setVideoURI(video);

        videoView.start();
    }
}
