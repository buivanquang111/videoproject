package com.example.video_do_an.trang_chu;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.video_do_an.SQLHelper;
import com.example.video_do_an.define.Define;
import com.example.video_do_an.R;
import com.example.video_do_an.databinding.VideoTrangchuBinding;
import com.example.video_do_an.define.Define_Methods;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Video_trangchu extends Fragment {
    private IOnClickPlayVideo listen;
    VideoTrangchuBinding binding;
    AdapterVideo adapterVideo;
    ArrayList<Video> videolist;
    SQLHelper sqlHelper;
    ArrayList<Video> videoArrayList;
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


        binding= DataBindingUtil.inflate(inflater, R.layout.video_trangchu,container,false);

        ActionViewFlipper();
        videolist =new ArrayList<>();

        new DoGetData(Define.STR_VIDEOHOT).execute();



        return binding.getRoot();
    }

    private void ActionViewFlipper() {
        ArrayList<String> mangquangcao = new ArrayList<>();

        mangquangcao.add(Define.STR_VIEWFLIPPER1);
        mangquangcao.add(Define.STR_VIEWFLIPPER2);
        mangquangcao.add(Define.STR_VIEWFLIPPER3);
        mangquangcao.add(Define.STR_VIEWFLIPPER4);
        mangquangcao.add(Define.STR_VIEWFLIPPER5);

        for (int i=0 ;i<mangquangcao.size(); i++){
            ImageView imageView =new ImageView(getContext());
            Picasso.with(getContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            binding.viewflipper.addView(imageView);
        }
        binding.viewflipper.setFlipInterval(3000);
        binding.viewflipper.setAutoStart(true);
        Animation animation_slie_in = AnimationUtils.loadAnimation(getContext(),R.anim.slide_in_right);
        Animation animation_slie_out = AnimationUtils.loadAnimation(getContext(),R.anim.slide_out_right);
        binding.viewflipper.setInAnimation(animation_slie_in);
        binding.viewflipper.setOutAnimation(animation_slie_out);

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
                        Toast.makeText(getContext(),"click video",Toast.LENGTH_LONG).show();

                        sqlHelper = new SQLHelper(getContext());
                        videoArrayList = sqlHelper.getAllItem();
                        if (videoArrayList.isEmpty()==false && define_methods.CHECK(video.getText(),videoArrayList)){
                            sqlHelper.deleteItem(video.getText());
                        }
                        sqlHelper.insertItem(video);


                        listen.onClickplayvideo(video);

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
