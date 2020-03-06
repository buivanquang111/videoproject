package com.example.video_do_an.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.os.CancellationSignal;
import android.view.MenuItem;

import android.widget.Toast;

import com.example.video_do_an.FragAddSave;
import com.example.video_do_an.R;
import com.example.video_do_an.databinding.ActivityMainBinding;
import com.example.video_do_an.fragHistory;
import com.example.video_do_an.playvideo.Playvideo_thinhhanh;
import com.example.video_do_an.playvideo.Playvideo_trangchu;

import com.example.video_do_an.thinh_hanh.IOnClickPlayThinhHanh;
import com.example.video_do_an.thinh_hanh.Thinhhanh;
import com.example.video_do_an.thinh_hanh.Video_thinhhanh;
import com.example.video_do_an.trang_chu.IOnClickPlayVideo;
import com.example.video_do_an.trang_chu.Video;
import com.example.video_do_an.trang_chu.Video_trangchu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.function.Consumer;


public class MainActivity extends AppCompatActivity implements IOnClickPlayVideo, IOnClickPlayThinhHanh {


    ActivityMainBinding binding;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);

        getFragment(new Video_trangchu());



        //thanh Bottomnavigation
        BottomNavigationView navigations=(BottomNavigationView) findViewById(R.id.navigation);
        navigations.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.navigation_trangchu){
                    getFragment(new Video_trangchu());
                    return true;
                }
                else if(menuItem.getItemId() == R.id.navigation_thinhhanh){
                    getFragment(new Video_thinhhanh());
                    return  true;
                }
                else if (menuItem.getItemId() == R.id.navigation_amnhac){
                    getFragment(new fragHistory());
                    return true;
                }
                else if(menuItem.getItemId() == R.id.navigation_game){
                    getFragment(new FragAddSave());
                    return true;
                }
                else if(menuItem.getItemId() == R.id.navigation_gam){
                    Toast.makeText(getBaseContext(),"Game",Toast.LENGTH_LONG).show();
                    return true;
                }

                return false;
            }
        });



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

    @Override
    public void onClickplayvideo(Video video) {
        getFragment(Playvideo_trangchu.newInstance(video));
    }

    @Override
    public void onclickplaythinhhanh(Thinhhanh thinhhanh){
        getFragment(Playvideo_thinhhanh.newInstance(thinhhanh));
    }
}

