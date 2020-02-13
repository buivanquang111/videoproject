package com.example.video_do_an.playvideo;


import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.video_do_an.R;
import com.example.video_do_an.databinding.PlayvideoTrangchuBinding;
import com.example.video_do_an.trang_chu.Video;

public class Playvideo_trangchu extends Fragment{

    MediaPlayer mediaPlayer;
    String link_mp4;
    String title;
    PlayvideoTrangchuBinding binding;
    private double startTime;
    private double finalTime;
    private int forwardTime = 5000;
    private int backwardTime = 5000;




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



        binding.imgplayvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.playvideoview.pause();
                binding.imgplayvideo.setVisibility(View.GONE);
                binding.imgpasevideo.setVisibility(View.VISIBLE);
            }
        });
        binding.imgpasevideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.playvideoview.start();
                binding.imgplayvideo.setVisibility(View.VISIBLE);
                binding.imgpasevideo.setVisibility(View.GONE);
            }
        });


        startTime = binding.playvideoview.getCurrentPosition();
        finalTime = binding.playvideoview.getDuration();


        binding.imgtuanhanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = (int) startTime;
                if((temp+forwardTime)<=finalTime){
                    startTime = startTime + forwardTime;
                    binding.playvideoview.seekTo((int)startTime);
                    Toast.makeText(getContext(),"jump forward 5 seconds",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getContext(),"cannot jump forward 5 second",Toast.LENGTH_LONG).show();
                }
            }
        });


        return binding.getRoot();
    }
}
