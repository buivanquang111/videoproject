package com.example.video_do_an.playvideo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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
import com.example.video_do_an.SQL.SQLHelperSave;
import com.example.video_do_an.databinding.PlayvideoThinhhanhBinding;
import com.example.video_do_an.define.Define_Methods;
import com.example.video_do_an.Contact.Thinhhanh;
import com.example.video_do_an.Contact.Video;

import java.util.ArrayList;

public class Playvideo_thinhhanh extends Fragment {
    PlayvideoThinhhanhBinding binding;
    Handler myHandler = new Handler();
    String title;
    String filemp4;
    String image;
    double currentposition,totalduration;
    Thinhhanh thinhhanhsave;

    AudioManager audioManager;
    int maxVol, stepVol, currentVol;
    int x1,y1;
    int time;
    boolean reChangeVol = true;
    boolean reChangePosition = true;

    SQLHelperSave sqlHelperSave;
    ArrayList<Video> arrayListThinhHanhSave;
    Define_Methods define_methods = new Define_Methods();


    public static Playvideo_thinhhanh newInstance(Thinhhanh thinhhanh){
        Bundle args= new Bundle();
        Playvideo_thinhhanh fragment = new Playvideo_thinhhanh();
        args.putSerializable("filemp4",thinhhanh.getFilemp4());
        args.putSerializable("title",thinhhanh.getTitle());
        args.putSerializable("image",thinhhanh.getAvatar());
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       binding = DataBindingUtil.inflate(inflater, R.layout.playvideo_thinhhanh,container,false);

       image = (String)getArguments().getSerializable("image");

       title = (String)getArguments().getSerializable("title");
       binding.tvplayvideoviewthinhhanh.setText(title);

       filemp4 = (String)getArguments().getSerializable("filemp4");
        Uri video = Uri.parse(filemp4);

        thinhhanhsave = new Thinhhanh(image,title,filemp4);
        binding.playvideoviewthinhhanh.setVideoURI(video);
       binding.playvideoviewthinhhanh.requestFocus();
       binding.playvideoviewthinhhanh.start();



        //seekbar
        binding.playvideoviewthinhhanh.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                setVideoProgress();
            }
        });

        //diplay controll
        Display display = new Display();
        myHandler.postDelayed(display,5000);
        // phần tua đâu

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

        //addsave
        binding.saveyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"add save video",Toast.LENGTH_LONG).show();
                addSave(thinhhanhsave);
            }
        });





        // setUp Volume and Position
        audioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        maxVol = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        stepVol = 100 / maxVol;
        currentVol = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        currentVol = currentVol * stepVol;
        binding.tvCurrentVol.setText(String.valueOf(currentVol));


    // Control Volume and Position
        binding.playvideoviewthinhhanh.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = (int) motionEvent.getX();
                        y1 = (int) motionEvent.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        binding.currentime.setText(timeConversion(binding.playvideoviewthinhhanh.getDuration()));
                        if (Math.abs(motionEvent.getX() - x1) > 50) {
                            binding.rvtimeevent.setVisibility(View.VISIBLE);
                            int timeCurent = (binding.playvideoviewthinhhanh.getCurrentPosition() + (int) motionEvent.getX() - x1);
                            binding.playvideoviewthinhhanh.seekTo(timeCurent);
                            binding.curenposition.setText(timeConversion(timeCurent));
                        }

                        if (Math.abs(motionEvent.getY() - y1) > 50) {
                            binding.changeVol.setVisibility(View.VISIBLE);
                            if (motionEvent.getY() - y1 < 0 && currentVol < 100) {
                                currentVol++;
                                binding.tvCurrentVol.setText(String.valueOf(currentVol));
                                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVol / stepVol, 0);

                            } else if (motionEvent.getY() - y1 > 0 && currentVol > 0) {
                                currentVol--;
                                binding.tvCurrentVol.setText(String.valueOf(currentVol));
                                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVol / stepVol, 0);

                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        binding.rvtimeevent.setVisibility(View.INVISIBLE);
                        binding.changeVol.setVisibility(View.INVISIBLE);
                        reChangeVol = true;
                        reChangePosition = true;
                        break;
                }
                binding.rvcontroll.setVisibility(View.VISIBLE);
                return true;
            }
        });

        //fullscreen
        binding.fullscreenTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvplayvideoviewthinhhanh.setVisibility(View.GONE);
                binding.exitfullscreenTT.setVisibility(View.VISIBLE);
                binding.fullscreenTT.setVisibility(View.GONE);
                time = binding.playvideoviewthinhhanh.getCurrentPosition();
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) binding.rlVideoView.getLayoutParams();

                params1.width = ViewGroup.LayoutParams.MATCH_PARENT;
                params1.height = ViewGroup.LayoutParams.MATCH_PARENT;

                binding.rlVideoView.setLayoutParams(params1);
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                binding.playvideoviewthinhhanh.seekTo(time);

            }
        });

        //exit fullscreen
        binding.exitfullscreenTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.fullscreenTT.setVisibility(View.VISIBLE);
                binding.exitfullscreenTT.setVisibility(View.GONE);
                time = binding.playvideoviewthinhhanh.getCurrentPosition();
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) binding.rlVideoView.getLayoutParams();

                params1.width = ViewGroup.LayoutParams.MATCH_PARENT;
                params1.height = 600;

                binding.rlVideoView.setLayoutParams(params1);
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                binding.playvideoviewthinhhanh.seekTo(time);



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
    class Display implements Runnable{

        @Override
        public void run() {
            myHandler.postDelayed(this,5000);
            binding.rvcontroll.setVisibility(View.GONE);
        }
    }

    public void addSave(Thinhhanh thinhHanhAddSave){
        Video item = new Video(thinhHanhAddSave.getAvatar(),thinhHanhAddSave.getTitle(),thinhHanhAddSave.getFilemp4());
        sqlHelperSave = new SQLHelperSave(getContext());
        arrayListThinhHanhSave = sqlHelperSave.getALLItem();
        if(arrayListThinhHanhSave.isEmpty()==false && define_methods.CHECK(item.getText(),arrayListThinhHanhSave)){
            sqlHelperSave.deleteItem(item.getText());
        }
        sqlHelperSave.insertItem(item);

    }

}
