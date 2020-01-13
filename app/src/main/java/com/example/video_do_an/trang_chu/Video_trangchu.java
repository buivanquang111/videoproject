package com.example.video_do_an.trang_chu;

import android.content.Intent;
import android.os.AsyncTask;
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

import com.example.video_do_an.Defile;
import com.example.video_do_an.Main2Activity;
import com.example.video_do_an.MainActivity;
import com.example.video_do_an.R;
import com.example.video_do_an.databinding.VideoTrangchuBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Video_trangchu extends Fragment {

    VideoTrangchuBinding binding;
    AdapterVideo adapterVideo;
    ArrayList<Video> videolist;

    public static Video_trangchu newInstance(){
        Bundle args=new Bundle();
        Video_trangchu fragment=new Video_trangchu();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding= DataBindingUtil.inflate(inflater, R.layout.video_trangchu,container,false);


        videolist =new ArrayList<>();

        new DoGetData(Defile.STR_VIDEOHOT).execute();


        return binding.getRoot();
    }
    public class DoGetData extends AsyncTask<Void, Void, Void> {
        String urlApi;
        String result="";

        public DoGetData(String urlApi) {
            this.urlApi = urlApi;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            binding.progressbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                URL url=new URL(urlApi);
                URLConnection connection = url.openConnection();
                InputStream is= connection.getInputStream();
                int bytecharacter;
                while (( bytecharacter = is.read()) != -1){
                    result +=(char) bytecharacter;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                JSONArray jsonArray=new JSONArray(result);
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    String avatar=jsonObject.getString("avatar");
                    String title=jsonObject.getString("title");
                    String mp4=jsonObject.getString("file_mp4");

                    videolist.add(new Video(avatar,title,mp4));

                }
                //adapterVideo.notifyDataSetChanged();
                adapterVideo=new AdapterVideo(videolist);

                adapterVideo.setiOnClickPlayVideo(new IOnClickPlayVideo() {
                    @Override
                    public void onClickplayvideo(Video video) {
                        Intent intent= new Intent(getContext(), Main2Activity.class);
                        intent.putExtra("link_mp4", video.getMp4());
                        intent.putExtra("title_mp4",video.getText());
                        startActivity(intent);
                    }
                });
                binding.listTrangchu.setAdapter(adapterVideo);
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false);
                binding.listTrangchu.setLayoutManager(layoutManager);
                binding.progressbar.setVisibility(View.GONE);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
