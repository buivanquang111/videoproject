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
import com.example.video_do_an.SQL.SQLHelperSave;
import com.example.video_do_an.databinding.FragaddsaveBinding;
import com.example.video_do_an.define.Define_Methods;
import com.example.video_do_an.Adapter.AdapterTrangChu;
import com.example.video_do_an.Interface.IOnClickPlayVideo;
import com.example.video_do_an.Contact.Video;

import java.util.ArrayList;

public class FragAddSave extends Fragment {

    FragaddsaveBinding binding;
    SQLHelper sqlHelper;
    ArrayList<Video> arrayList;
    ArrayList<Video> arrayListSQLSave;
    SQLHelperSave sqlHelperSave;
    Define_Methods define_methods = new Define_Methods();
    AdapterTrangChu adapterVideo;
    private IOnClickPlayVideo listen;

    public static FragAddSave newInstance(){
        Bundle args=new Bundle();
        FragAddSave fragment=new FragAddSave();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragaddsave,container,false);

        sqlHelperSave = new SQLHelperSave(getContext());
        arrayListSQLSave = sqlHelperSave.getALLItem();
        arrayList = new ArrayList<>();

        binding.tvxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlHelperSave.deleteAll();
                arrayListSQLSave = sqlHelperSave.getALLItem();
                adapterVideo = new AdapterTrangChu(arrayListSQLSave);
                binding.rvyeuthich.setAdapter(adapterVideo);
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1, RecyclerView.VERTICAL, false);
                binding.rvyeuthich.setLayoutManager(layoutManager);
            }
        });

        int size = arrayListSQLSave.size();
        for(int i=size-1;i>=0;i--){
            arrayList.add(arrayListSQLSave.get(i));
        }
        adapterVideo = new AdapterTrangChu(arrayList);

        adapterVideo.setiOnClickPlayVideo(new IOnClickPlayVideo() {
            @Override
            public void onClickplayvideo(Video video) {
                sqlHelper = new SQLHelper(getContext());
                arrayList = sqlHelper.getAllItem();
                if (arrayList.isEmpty()==false && define_methods.CHECK(video.getText(),arrayList)){
                    sqlHelper.deleteItem(video.getText());
                }
                sqlHelper.insertItem(video);
                listen.onClickplayvideo(video);
            }
        });
        binding.rvyeuthich.setAdapter(adapterVideo);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1, RecyclerView.VERTICAL, false);
        binding.rvyeuthich.setLayoutManager(layoutManager);
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
