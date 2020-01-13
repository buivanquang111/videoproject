package com.example.video_do_an.thinh_hanh;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.video_do_an.Defile;
import com.example.video_do_an.R;
import com.example.video_do_an.databinding.ThinhHanhBinding;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Video_thinhhanh extends Fragment {

        //String jsonarr="http://demo4855049.mockable.io/GetCategory";
        ThinhHanhBinding binding;
        AdapterThinhhanh adapterThinhhanh;
        ArrayList<Thinhhanh> thinhhanhlist;

        public static Video_thinhhanh newInstance(){
            Bundle args=new Bundle();
            Video_thinhhanh fragment= new Video_thinhhanh();
            fragment.setArguments(args);
            return fragment;
        }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.thinh_hanh,container,false);

        thinhhanhlist =new ArrayList<>();

        new DoGetData(Defile.STR_CATEGORY).execute();

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
            binding.progressbathinhhanh.setVisibility(View.VISIBLE);
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
                    String thumb=jsonObject.getString("thumb");
                    String title=jsonObject.getString("title");

                    thinhhanhlist.add(new Thinhhanh(thumb,title));
                }
                //adapterThinhhanh.notifyDataSetChanged();
                adapterThinhhanh=new AdapterThinhhanh(thinhhanhlist);
                binding.listThinhhanh.setAdapter(adapterThinhhanh);
                RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
                binding.listThinhhanh.setLayoutManager(layoutManager);
                binding.progressbathinhhanh.setVisibility(View.GONE);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }



}
