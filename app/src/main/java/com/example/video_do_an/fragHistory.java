package com.example.video_do_an;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.video_do_an.databinding.FraghistoryBinding;
import com.example.video_do_an.define.Define_Methods;
import com.example.video_do_an.trang_chu.AdapterVideo;
import com.example.video_do_an.trang_chu.IOnClickPlayVideo;
import com.example.video_do_an.trang_chu.Video;
import com.example.video_do_an.trang_chu.Video_trangchu;

import java.util.ArrayList;

public class fragHistory extends Fragment {
    FraghistoryBinding binding;
    SQLHelper sqlHelper;
    ArrayList<Video> videoArrayList;
    ArrayList<Video> arrayList;
    AdapterVideo adapterVideo;
    Define_Methods define_methods = new Define_Methods();



    public static Video_trangchu newInstance(){
        Bundle args=new Bundle();
        Video_trangchu fragment=new Video_trangchu();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.fraghistory,container,false);

        videoArrayList = new ArrayList<>();
        sqlHelper = new SQLHelper(getContext());
        videoArrayList = sqlHelper.getAllItem();
        arrayList = new ArrayList<>();
        int size = videoArrayList.size();
        for (int i=size-1;i>=0;i--){
            arrayList.add(videoArrayList.get(i));
        }

        adapterVideo = new AdapterVideo(arrayList);
        adapterVideo.setiOnClickPlayVideo(new IOnClickPlayVideo() {
            @Override
            public void onClickplayvideo(Video video) {
                if (arrayList.isEmpty()==false && define_methods.CHECK(video.getText(),arrayList)){
                    sqlHelper.deleteItem(video.getText());
                }
                sqlHelper.insertItem(video);

            }
        });
        binding.fraghistory.setAdapter(adapterVideo);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1, RecyclerView.VERTICAL, false);
        binding.fraghistory.setLayoutManager(layoutManager);
        return binding.getRoot();
    }
}
