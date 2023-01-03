package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project.Model.Asset;
import com.example.project.Model.ListAsset;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LauncherActivity extends AppCompatActivity {
    ImageView imageView;
    TextView tv1,tv2;
    Animation top,bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);



        imageView= (ImageView) findViewById(R.id.ic_logo);
        tv1= (TextView) findViewById(R.id.textView1);
        tv2= (TextView) findViewById(R.id.textView2);

        top = AnimationUtils.loadAnimation(this,R.anim.top);
        bottom = AnimationUtils.loadAnimation(this,R.anim.bottom);

        imageView.setAnimation(top);
        tv1.setAnimation(bottom);
        tv2.setAnimation(bottom);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(LauncherActivity.this,MainActivity.class));
                finish();

            }
        },2000);

    }
}