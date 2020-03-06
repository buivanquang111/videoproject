package com.example.video_do_an.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.video_do_an.R;
import com.example.video_do_an.Interface.IOnClickPlayVideo;
import com.example.video_do_an.Contact.Video;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterTrangChu extends RecyclerView.Adapter<AdapterTrangChu.Viewhoder> {

    Context context;
    ArrayList<Video> videos;
    IOnClickPlayVideo iOnClickPlayVideo;

    public AdapterTrangChu(ArrayList<Video> videos) {
        this.videos = videos;
    }

    public void setiOnClickPlayVideo(IOnClickPlayVideo iOnClickPlayVideo) {
        this.iOnClickPlayVideo = iOnClickPlayVideo;
    }

    @NonNull
    @Override
    public AdapterTrangChu.Viewhoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.item_trangchu,parent,false);
        context = parent.getContext();
        Viewhoder viewhoder=new Viewhoder(view);

        return viewhoder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTrangChu.Viewhoder holder, final int position) {
        final Video video=videos.get(position);

        String imgurl=video.getImg();
        String tvurl=video.getText();

        holder.tv_item_video.setText(tvurl);
        Picasso.with(context).load(imgurl).into(holder.img_item_video);
        holder.img_item_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOnClickPlayVideo.onClickplayvideo(video);
            }
        });


    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public class Viewhoder extends RecyclerView.ViewHolder {
        public ImageView img_item_video;
        public TextView tv_item_video;

        public Viewhoder(@NonNull View itemView) {
            super(itemView);
            img_item_video=itemView.findViewById(R.id.img_item_video);
            tv_item_video=itemView.findViewById(R.id.tv_item_video);
        }
    }
}
