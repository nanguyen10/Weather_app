package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project.Model.Asset;
import com.example.project.Model.ListAsset;
import com.example.project.Model.Map;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    APIInterface apiInterface;
    ArrayList<Asset> weatherAsset= new ArrayList<>();
    ArrayList<Asset> respondAsset= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        CallAPI();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this,MainActivity3.class));
                finish();

            }
        },2000);

    }

    private void CallAPI() {

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

        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<List<Asset>> call1 = apiInterface.getCurrent();
        call1.enqueue(new Callback<List<Asset>>() {
            @Override
            public void onResponse(Call<List<Asset>> call, Response<List<Asset>> response) {
                Log.d("API CALL", response.code()+"");
                respondAsset = (ArrayList<Asset>) response.body();

                for (Asset asset: respondAsset) {
                    if(asset.type.equals("WeatherAsset")){
                        asset.coordinates = asset.attributes.getAsJsonObject().get("location").getAsJsonObject().get("value").getAsJsonObject().get("coordinates").getAsJsonArray();
                        weatherAsset.add(asset);
                    }
                }
                ListAsset.list = weatherAsset;

            }

            @Override
            public void onFailure(Call<List<Asset>> call, Throwable t) {
                Log.d("API CALL", t.getMessage().toString());
            }
        });
        apiInterface = APIClient.getClient().create(APIInterface.class);

    }
}