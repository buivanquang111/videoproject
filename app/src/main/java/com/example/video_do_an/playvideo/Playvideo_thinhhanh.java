package com.example.video_do_an.playvideo;

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
import com.example.video_do_an.databinding.PlayvideoThinhhanhBinding;
import com.example.video_do_an.thinh_hanh.Thinhhanh;

public class Playvideo_thinhhanh extends Fragment {
    PlayvideoThinhhanhBinding binding;
    String title;
    String filemp4;

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
        return binding.getRoot();
    }
}
