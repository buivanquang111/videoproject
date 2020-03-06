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
import com.example.video_do_an.Interface.IOnClickPlayThinhHanh;
import com.example.video_do_an.Contact.Thinhhanh;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterThinhhanh extends RecyclerView.Adapter<AdapterThinhhanh.Viewhoder> {

    Context context;
    ArrayList<Thinhhanh> thinhhanhs;
    IOnClickPlayThinhHanh iOnClickPlayThinhHanh;

    public AdapterThinhhanh(ArrayList<Thinhhanh> thinhhanhs) {
        this.thinhhanhs = thinhhanhs;
    }

    public void setiOnClickPlayThinhHanh(IOnClickPlayThinhHanh iOnClickPlayThinhHanh) {
        this.iOnClickPlayThinhHanh = iOnClickPlayThinhHanh;
    }

    @NonNull
    @Override
    public AdapterThinhhanh.Viewhoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view= inflater.inflate(R.layout.item_thinhhanh,parent,false);
        context=parent.getContext();
        Viewhoder viewhoder=new Viewhoder(view);

        return viewhoder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterThinhhanh.Viewhoder holder, int position) {

        final Thinhhanh thinhhanh=thinhhanhs.get(position);

        String avatar= thinhhanh.getAvatar();
        String title= thinhhanh.getTitle();

        holder.tv_thinhhanh.setText(title);
        Picasso.with(context).load(avatar).into(holder.img_thinhhanh);
        holder.img_thinhhanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOnClickPlayThinhHanh.onclickplaythinhhanh(thinhhanh);
            }
        });

    }

    @Override
    public int getItemCount() {
        return thinhhanhs.size();
    }

    public class Viewhoder extends RecyclerView.ViewHolder {

        ImageView img_thinhhanh;
        TextView tv_thinhhanh;
        public Viewhoder(@NonNull View itemView) {
            super(itemView);

            img_thinhhanh=itemView.findViewById(R.id.img_thinhhanh);
            tv_thinhhanh=itemView.findViewById(R.id.tv_thinhhanh);
        }
    }
}
