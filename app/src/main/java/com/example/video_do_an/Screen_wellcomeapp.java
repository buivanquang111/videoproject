package com.example.video_do_an;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Screen_wellcomeapp extends AppCompatActivity {

    ImageView logo;
    TextView textView;
    static int splashtime=5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_wellcomeapp);

        logo = findViewById(R.id.screenwellcome);
        textView = findViewById(R.id.tvname);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(getBaseContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        },splashtime);
        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.myscreenwellcome);
        logo.startAnimation(myanim);
        textView.startAnimation(myanim);
    }
}
