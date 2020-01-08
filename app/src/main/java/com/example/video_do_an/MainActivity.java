package com.example.video_do_an;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.video_do_an.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);

        ActionBar actionBar =getSupportActionBar();
        actionBar.hide();

        getFragment(new Video_trangchu());

        binding.trangchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(),MainActivity.class);
                startActivity(intent);
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

