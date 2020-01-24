package com.example.video_do_an;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.video_do_an.databinding.ActivityMainBinding;
import com.example.video_do_an.thinh_hanh.Video_thinhhanh;
import com.example.video_do_an.trang_chu.Video_trangchu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


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
                    Toast.makeText(getBaseContext(),"am nhac",Toast.LENGTH_LONG).show();
                    return true;
                }
                else if(menuItem.getItemId() == R.id.navigation_game){
                    Toast.makeText(getBaseContext(),"Game",Toast.LENGTH_LONG).show();
                    return true;
                }
                else if(menuItem.getItemId() == R.id.navigation_gam){
                    Toast.makeText(getBaseContext(),"Gam",Toast.LENGTH_LONG).show();
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

}

