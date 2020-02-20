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
import com.example.video_do_an.databinding.PlayvideoThinhhanhBinding;
import com.example.video_do_an.thinh_hanh.Thinhhanh;
import com.example.video_do_an.thinh_hanh.Video_thinhhanh;

public class Playvideo_thinhhanh extends Fragment {
    PlayvideoThinhhanhBinding binding;
    String title;
    String filemp4;
    double currentposition,totalduration;

    public static Playvideo_thinhhanh newInstance(Thinhhanh thinhhanh){
        Bundle args= new Bundle();
        Playvideo_thinhhanh fragment = new Playvideo_thinhhanh();
        args.putSerializable("filemp4",thinhhanh.getFilemp4());
        args.putSerializable("title",thinhhanh.getTitle());
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       binding = DataBindingUtil.inflate(inflater, R.layout.playvideo_thinhhanh,container,false);
       title = (String)getArguments().getSerializable("title");
       binding.tvplayvideoviewthinhhanh.setText(title);

       filemp4 = (String)getArguments().getSerializable("filemp4");
        Uri video = Uri.parse(filemp4);
        binding.playvideoviewthinhhanh.setVideoURI(video);
       binding.playvideoviewthinhhanh.requestFocus();
       binding.playvideoviewthinhhanh.start();

        //gọi fragment
        getFragmentManager().beginTransaction().replace(R.id.frameplaythinhhanh,new Video_thinhhanh()).commit();

        //seekbar
        binding.playvideoviewthinhhanh.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                setVideoProgress();
            }
        });

        //dừng video
       binding.imgpausevideoTT.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               binding.playvideoviewthinhhanh.pause();
               binding.imgplayvideoTT.setVisibility(View.VISIBLE);
               binding.imgpausevideoTT.setVisibility(View.GONE);
           }
       });

       //chạy video
       binding.imgplayvideoTT.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               binding.playvideoviewthinhhanh.start();
               binding.imgplayvideoTT.setVisibility(View.GONE);
               binding.imgpausevideoTT.setVisibility(View.VISIBLE);
           }
       });

       //tua nhanh 5s
        binding.imgtuanhanhTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = binding.playvideoviewthinhhanh.getCurrentPosition();
                int duration = binding.playvideoviewthinhhanh.getDuration();
                int time = 5000;
                if(current+time<duration){
                    binding.playvideoviewthinhhanh.seekTo(current+time);
                    Toast.makeText(getContext(),"jump forward 5 seconds",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getContext(),"cannot jump forward 5 seconds",Toast.LENGTH_LONG).show();
                }

            }
        });

        //tua lùi 5s
        binding.imgtuachamTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = binding.playvideoviewthinhhanh.getCurrentPosition();
                int time = 5000;
                if(current-time>=0){
                    binding.playvideoviewthinhhanh.seekTo(current-time);
                    Toast.makeText(getContext(),"jump backward 5 seconds",Toast.LENGTH_LONG).show();
                }
                else{
                    binding.playvideoviewthinhhanh.seekTo(0);
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

    //thanh seekbar
    public void setVideoProgress(){
        currentposition = binding.playvideoviewthinhhanh.getCurrentPosition();
        totalduration = binding.playvideoviewthinhhanh.getDuration();

        binding.totaldurationTT.setText(timeConversion((int) totalduration));
        binding.currentpositionTT.setText(timeConversion((int) currentposition));
        binding.seekbarTT.setMax((int) totalduration);
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                currentposition = binding.playvideoviewthinhhanh.getCurrentPosition();
                binding.currentpositionTT.setText(timeConversion((long)currentposition));
                binding.seekbarTT.setProgress((int) currentposition);
                handler.postDelayed(this,1000);
            }
        };
        handler.postDelayed(runnable,1000);

        //seekbar change listener
        binding.seekbarTT.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                currentposition = binding.seekbarTT.getProgress();
                binding.playvideoviewthinhhanh.seekTo((int) currentposition);
            }
        });

    }

}
