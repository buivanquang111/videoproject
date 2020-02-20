package com.example.video_do_an.playvideo;



import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;


import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.video_do_an.R;
import com.example.video_do_an.databinding.PlayvideoTrangchuBinding;
import com.example.video_do_an.thinh_hanh.Video_thinhhanh;
import com.example.video_do_an.trang_chu.Video;
import com.example.video_do_an.trang_chu.Video_trangchu;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class Playvideo_trangchu extends Fragment {



    Handler myHandler = new Handler();

    String link_mp4;
    String title;
    PlayvideoTrangchuBinding binding;
    double currentposition,totalduration;



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

        //goi fragment
        getFragmentManager().beginTransaction().replace(R.id.frameplaytrangchu,new Video_thinhhanh()).commit();

        //seekbar
        binding.playvideoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                setVideoProgress();
            }
        });

        //dừng video
        binding.imgpausevideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.playvideoview.pause();
                binding.imgplayvideo.setVisibility(View.VISIBLE);
                binding.imgpausevideo.setVisibility(View.GONE);
            }
        });

        //chạy video
        binding.imgplayvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.playvideoview.start();
                binding.imgplayvideo.setVisibility(View.GONE);
                binding.imgpausevideo.setVisibility(View.VISIBLE);

            }
        });

        //tua video nhanh 5s
        binding.imgtuanhanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = binding.playvideoview.getCurrentPosition();
                int duration = binding.playvideoview.getDuration();
                int time = 5000;
                if(current+time<duration){
                    binding.playvideoview.seekTo(current+time);
                    Toast.makeText(getContext(),"jump forward 5 seconds",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getContext(),"cannot jump forward 5 seconds",Toast.LENGTH_LONG).show();
                }

            }
        });

        //lùi video 5s
        binding.imgtuacham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = binding.playvideoview.getCurrentPosition();
                int time = 5000;
                if(current-time>=0){
                    binding.playvideoview.seekTo(current-time);
                    Toast.makeText(getContext(),"jump backward 5 seconds",Toast.LENGTH_LONG).show();
                }
                else{
                    binding.playvideoview.seekTo(0);
                    Toast.makeText(getContext(),"cannot jump backward 5 seconds",Toast.LENGTH_LONG).show();
                }
            }
        });



        return binding.getRoot();
    }

    //time
    public String timeConversion(long value){
        String time;
        int dur = (int) value;
        int hrs = (dur/3600000);
        int mns = (dur/60000)%60000;
        int scs = dur % 60000/1000;
        if(hrs > 0){
            time = String.format("%02d:%02d:%02d",hrs,mns,scs);
        }else{
            time = String.format("%02d:%02d",mns,scs);
        }
        return time;
    }

    public void setVideoProgress(){
        currentposition = binding.playvideoview.getCurrentPosition();
        totalduration = binding.playvideoview.getDuration();

        binding.totalduration.setText(timeConversion((int) totalduration));
        binding.currentposition.setText(timeConversion((int) currentposition));
        binding.seekbar.setMax((int) totalduration);
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                currentposition = binding.playvideoview.getCurrentPosition();
                binding.currentposition.setText(timeConversion((long)currentposition));
                binding.seekbar.setProgress((int) currentposition);
                handler.postDelayed(this,1000);
            }
        };
        handler.postDelayed(runnable,1000);

        //seekbar change listener
        binding.seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                currentposition = binding.seekbar.getProgress();
                binding.playvideoview.seekTo((int) currentposition);
            }
        });

    }


}
