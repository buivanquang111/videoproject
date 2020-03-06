package com.example.video_do_an.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.video_do_an.R;
import com.example.video_do_an.SQL.SQLHelper;
import com.example.video_do_an.databinding.FraghistoryBinding;
import com.example.video_do_an.define.Define_Methods;
import com.example.video_do_an.Adapter.AdapterTrangChu;
import com.example.video_do_an.Interface.IOnClickPlayVideo;
import com.example.video_do_an.Contact.Video;


import java.util.ArrayList;

public class FragHistory extends Fragment {
    FraghistoryBinding binding;
    SQLHelper sqlHelper;
    ArrayList<Video> videoArrayList;
    ArrayList<Video> arrayList;
    AdapterTrangChu adapterVideo;
    Define_Methods define_methods = new Define_Methods();
    private IOnClickPlayVideo listen;



    public static FragHistory newInstance(){
        Bundle args=new Bundle();
        FragHistory fragment=new FragHistory();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.fraghistory,container,false);

        //videoArrayList = new ArrayList<>();
        sqlHelper = new SQLHelper(getContext());
        videoArrayList = sqlHelper.getAllItem();
        arrayList = new ArrayList<>();

        binding.tvdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlHelper.deleteAll();
                videoArrayList = sqlHelper.getAllItem();
                adapterVideo = new AdapterTrangChu(videoArrayList);
                binding.fraghistory.setAdapter(adapterVideo);
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1, RecyclerView.VERTICAL, false);
                binding.fraghistory.setLayoutManager(layoutManager);
            }
        });

        int size = videoArrayList.size();
        for (int i=size-1;i>=0;i--){
            arrayList.add(videoArrayList.get(i));
        }

        adapterVideo = new AdapterTrangChu(arrayList);
        adapterVideo.setiOnClickPlayVideo(new IOnClickPlayVideo() {
            @Override
            public void onClickplayvideo(Video video) {
                if (arrayList.isEmpty()==false && define_methods.CHECK(video.getText(),arrayList)){
                    sqlHelper.deleteItem(video.getText());
                }
                sqlHelper.insertItem(video);

                listen.onClickplayvideo(video);
            }
        });
        binding.fraghistory.setAdapter(adapterVideo);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1, RecyclerView.VERTICAL, false);
        binding.fraghistory.setLayoutManager(layoutManager);
        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof IOnClickPlayVideo) {
            listen = (IOnClickPlayVideo) context;
        } else {
            throw new RuntimeException(context.toString() + "must implement");
        }

    }
}
