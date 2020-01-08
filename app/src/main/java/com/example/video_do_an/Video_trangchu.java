package com.example.video_do_an;

import android.os.AsyncTask;
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

import com.example.video_do_an.databinding.VideoTrangchuBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Video_trangchu extends Fragment {

    String jsonarr="http://demo4855049.mockable.io/gethotvideo";
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

        binding= DataBindingUtil.inflate(inflater,R.layout.video_trangchu,container,false);


        videolist =new ArrayList<>();

        new DoGetData(jsonarr).execute();
        adapterVideo=new AdapterVideo(videolist);
        binding.listTrangchu.setAdapter(adapterVideo);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false);
        binding.listTrangchu.setLayoutManager(layoutManager);

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

                    videolist.add(new Video(avatar,title));
                }
                adapterVideo.notifyDataSetChanged();
                binding.progressbar.setVisibility(View.GONE);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
