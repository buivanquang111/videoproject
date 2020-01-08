package com.example.video_do_an;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterVideo extends RecyclerView.Adapter<AdapterVideo.Viewhoder> {

    Context context;
    ArrayList<Video> videos;

    public AdapterVideo(ArrayList<Video> videos) {
        this.videos = videos;
    }

    @NonNull
    @Override
    public AdapterVideo.Viewhoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.item_video,parent,false);
        context = parent.getContext();
        Viewhoder viewhoder=new Viewhoder(view);

        return viewhoder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterVideo.Viewhoder holder, int position) {
        Video video=videos.get(position);

        String imgurl=video.getImg();
        String tvurl=video.getText();

        holder.tv_item_video.setText(tvurl);
        Picasso.with(context).load(imgurl).into(holder.img_item_video);


    }
// ảo thế nhỉ
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
