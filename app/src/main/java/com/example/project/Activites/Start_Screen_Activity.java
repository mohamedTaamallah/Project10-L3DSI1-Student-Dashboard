package com.example.project.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.VideoView;

import com.example.project.R;

public class Start_Screen_Activity extends AppCompatActivity {
   private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen);


        VideoView videoView = (VideoView) findViewById(R.id.videoView);
        videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.intro);
        videoView.start();


    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(!isFinishing())
            {
                startActivity(new Intent(getApplicationContext(), home_page_activity.class));
                finish();
            }
        }

    };

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable,3000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }
}
