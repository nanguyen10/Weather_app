package com.example.project;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.project.Model.Asset;
import com.example.project.Model.ListAsset;
import com.example.project.Model.WeatherData;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BroadCast extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"Database was updated!",Toast.LENGTH_LONG).show();
        DatabaseHandler db = new DatabaseHandler(context);
        List<Asset> list = ListAsset.list;
        for (Asset asset: list) {
            WeatherData  weatherData = new WeatherData();

            weatherData.setId(asset.id);
            weatherData.setName(asset.name);
            weatherData.setTemperature(Float.parseFloat(String.valueOf(asset.attributes.get("temperature").getAsJsonObject().get("value"))));
            weatherData.setHumidity(Float.parseFloat(String.valueOf(asset.attributes.get("humidity").getAsJsonObject().get("value"))));
            weatherData.setWindDirection(Float.parseFloat(String.valueOf(asset.attributes.get("windDirection").getAsJsonObject().get("value"))));
            weatherData.setWindSpeed(Float.parseFloat(String.valueOf(asset.attributes.get("windSpeed").getAsJsonObject().get("value"))));
            weatherData.setTime(String.valueOf(LocalDateTime.now()));

            db.addWeatherData(weatherData);
        }

    }


}
