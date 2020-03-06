package com.example.video_do_an.playvideo;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;


import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.video_do_an.R;
import com.example.video_do_an.SQLHelper;
import com.example.video_do_an.SQLHelperSave;
import com.example.video_do_an.databinding.PlayvideoTrangchuBinding;
import com.example.video_do_an.define.Define_Methods;
import com.example.video_do_an.trang_chu.AdapterVideo;
import com.example.video_do_an.trang_chu.IOnClickPlayVideo;
import com.example.video_do_an.trang_chu.Video;
import java.util.ArrayList;

public class Playvideo_trangchu extends Fragment {


    private IOnClickPlayVideo listen;
    Handler myHandler = new Handler();

    String link_mp4;
    String title;
    String img;
    int getTimeCurrent;
    PlayvideoTrangchuBinding binding;
    double currentposition,totalduration;
    int position=0;
    ArrayList<Video> arrayList;
    AdapterVideo adapterVideo;
    Video videosave;

    Define_Methods define_methods = new Define_Methods();
    SQLHelper sqlHelper;
    ArrayList<Video> arrayListSQL;
    SQLHelperSave sqlHelperSave;
    ArrayList<Video> arrayListVideoSave;




    public static Playvideo_trangchu newInstance(Video video){
        Bundle args = new Bundle();
        Playvideo_trangchu fragment = new Playvideo_trangchu();
        args.putSerializable("link_MP4", video.getMp4());
        args.putSerializable("title",video.getText());
        args.putSerializable("image",video.getImg());

        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.playvideo_trangchu,container,false);

        img = (String) getArguments().getSerializable("image");

         title =(String) getArguments().getSerializable("title");
         binding.tvplayvideoview.setText(title);
         link_mp4= (String) getArguments().getSerializable("link_MP4");
         final Uri video = Uri.parse(link_mp4);

         videosave = new Video(img,title,link_mp4);
        binding.playvideoview.setVideoURI(video);
        binding.playvideoview.requestFocus();
        binding.playvideoview.start();





        //seekbar
        binding.playvideoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                setVideoProgress();
            }
        });

        //diplay controll
        Display display = new Display();
        myHandler.postDelayed(display,5000);
        binding.playvideoview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                binding.control.setVisibility(View.VISIBLE);
                return false;
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




        //next video
        binding.imgnextvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"next video",Toast.LENGTH_LONG).show();

            }
        });

        //addsavevideo
        binding.savevideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"add save video",Toast.LENGTH_LONG).show();
                addSave(videosave);
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

    class Display implements Runnable{

        @Override
        public void run() {
            myHandler.postDelayed(this,5000);
            binding.control.setVisibility(View.GONE);
        }
    }

    public void addHistory(Video video){
        sqlHelper = new SQLHelper(getContext());
        arrayListSQL = sqlHelper.getAllItem();
        if (arrayListSQL.isEmpty() == false && define_methods.CHECK(video.getText(),arrayListSQL)){
            sqlHelper.deleteItem(video.getText());
        }
        sqlHelper.insertItem(video);

    }

    public void addSave(Video videoAddSave){
        sqlHelperSave = new SQLHelperSave(getContext());
        arrayListVideoSave = sqlHelperSave.getALLItem();
        if(arrayListVideoSave.isEmpty()==false && define_methods.CHECK(videoAddSave.getText(),arrayListVideoSave)){
            sqlHelperSave.deleteItem(videoAddSave.getText());
        }
        sqlHelperSave.insertItem(videoAddSave);

    }




}
