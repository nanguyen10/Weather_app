package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import com.example.project.Model.Asset;
import com.example.project.Model.ListAsset;
import com.example.project.Model.Map;
import com.example.project.Model.WeatherData;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin;
    VideoView videoView;

    @Override
    protected void onResume() {
        super.onResume();

        videoView= findViewById(R.id.video_view);
        Uri uri = Uri.parse("android.resource://com.example.project/" + R.raw.video_bg);
        videoView.setVideoURI(uri);
        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView= findViewById(R.id.video_view);
        Uri uri = Uri.parse("android.resource://com.example.project/" + R.raw.video_bg);
        videoView.setVideoURI(uri);
        videoView.start();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });

        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                startActivity(intent);
            }
        });
        /*AlarmHandler alarmHandler = new AlarmHandler(this);
        alarmHandler.cancelAlarmManager();
        alarmHandler.setAlarmManager();*/
    }


}