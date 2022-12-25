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

import com.example.project.Model.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    ImageView imageView;
    TextView tv1,tv2;
    Animation top,bottom;

    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<Map> call = apiInterface.getMap();
        call.enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                Log.d("API CALL", response.code()+"");
                Map.map = response.body();
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                Log.d("API CALL", t.getMessage().toString());
            }

        });
        apiInterface = APIClient.getClient().create(APIInterface.class);

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
                startActivity(new Intent(SplashActivity.this,MainActivity3.class));
                finish();

            }
        },2000);

    }
}