package com.example.video_do_an;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;

import com.example.video_do_an.databinding.ActivityMainBinding;
import com.example.video_do_an.databinding.ActivityMainBindingImpl;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String jsonarr="http://demo4855049.mockable.io/gethotvideo";
    ActivityMainBinding binding;
    AdapterVideo adapterVideo;
    ArrayList<Video> videolist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);

        videolist =new ArrayList<>();
        new DoGetData(jsonarr).execute();
        adapterVideo=new AdapterVideo(videolist);
        binding.listTrangchu.setAdapter(adapterVideo);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getBaseContext(), 2, RecyclerView.VERTICAL, false);
        binding.listTrangchu.setLayoutManager(layoutManager);


    }

    public class DoGetData extends AsyncTask<Void, Void, Void>{
        String urlApi;
        String result="";

        public DoGetData(String urlApi) {
            this.urlApi = urlApi;
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

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void getFragment(Fragment fragment){
        try {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.framelayout,fragment)
                    .commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

