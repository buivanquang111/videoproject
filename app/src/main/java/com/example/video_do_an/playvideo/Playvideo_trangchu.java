package com.example.video_do_an.playvideo;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.video_do_an.R;
import com.example.video_do_an.databinding.PlayvideoTrangchuBinding;
import com.example.video_do_an.trang_chu.Video;

public class Playvideo_trangchu extends Fragment{

    String link_mp4;
    String title;
    PlayvideoTrangchuBinding binding;


    public static Playvideo_trangchu newInstance(Video video){
        Bundle args = new Bundle();
        Playvideo_trangchu fragment = new Playvideo_trangchu();
        args.putSerializable("link_MP4", video.getMp4());
        args.putSerializable("title",video.getText());
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.playvideo_trangchu,container,false);


         title =(String) getArguments().getSerializable("title");
         binding.tvplayvideoview.setText(title);
         link_mp4= (String) getArguments().getSerializable("link_MP4");
        Uri video = Uri.parse(link_mp4);
        binding.playvideoview.setVideoURI(video);
        binding.playvideoview.requestFocus();
        binding.playvideoview.start();

        return binding.getRoot();
    }
}
