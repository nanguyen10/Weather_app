package com.example.project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.project.Model.Asset;
import com.example.project.Model.ListAsset;

import java.time.LocalDate;

public class DetailFragment extends Fragment {

    View view;
    ImageView img;
    TextView  tv_place,tv_date,tv_des,tv_asset,tv_humidity,tv_rain,tv_altitude,tv_azi,tv_irradiance,tv_zenith,tv_temp,tv_uv,tv_wea_data,tv_direc,tv_speed;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_detail,container,false);

        anhxa();
        initUI();
        return view;
    }

    private void anhxa() {
        img = (ImageView) view.findViewById(R.id.img_icon);

        tv_place = view.findViewById(R.id.tv_place);
        tv_date = view.findViewById(R.id.tv_date_val);
        tv_des  = (TextView) view.findViewById(R.id.tv_description);
        tv_asset= (TextView) view.findViewById(R.id.tv_asset_name);
        tv_humidity= (TextView) view.findViewById(R.id.tv_humidity_val);
        tv_rain= (TextView) view.findViewById(R.id.tv_rainfall_val);
        tv_altitude= (TextView) view.findViewById(R.id.tv_sun_altitude_val);
        tv_azi= (TextView) view.findViewById(R.id.tv_sun_azimuth_val);
        tv_irradiance= (TextView) view.findViewById(R.id.tv_sun_irradiance_val);
        tv_zenith= (TextView) view.findViewById(R.id.tv_sun_zenith_val);
        tv_temp= (TextView) view.findViewById(R.id.tv_temperature_val);
        tv_uv= (TextView) view.findViewById(R.id.tv_uv_index_val);
        tv_wea_data= (TextView) view.findViewById(R.id.tv_weather_data_val);
        tv_direc= (TextView) view.findViewById(R.id.tv_wind_direction_val);
        tv_speed= (TextView) view.findViewById(R.id.tv_wind_speed_val);
    }

    private void initUI() {
        String icon_id = "";
        Asset asset = ListAsset.assett;
        if(asset != null){
            tv_asset.setText(asset.name);
            tv_place.setText(asset.attributes.get("weatherData").getAsJsonObject().get("value").getAsJsonObject().get("name").toString().replace("\"",""));
            tv_date.setText(String.valueOf(LocalDate.now()));
            tv_des.setText(asset.attributes.get("weatherData").getAsJsonObject().get("value").getAsJsonObject().get("weather").getAsJsonArray().get(0).getAsJsonObject().get("description").toString().replace("\"",""));
            tv_humidity.setText(asset.attributes.get("humidity").getAsJsonObject().get("value").toString());
            tv_rain.setText(asset.attributes.get("rainfall").getAsJsonObject().get("value").toString());
            tv_altitude.setText(asset.attributes.get("rainfall").getAsJsonObject().get("value").toString());
            tv_azi.setText(asset.attributes.get("sunAzimuth").getAsJsonObject().get("value").toString());
            tv_irradiance.setText(asset.attributes.get("sunIrradiance").getAsJsonObject().get("value").toString());
            tv_zenith.setText(asset.attributes.get("sunZenith").getAsJsonObject().get("value").toString());
            tv_temp.setText(asset.attributes.get("temperature").getAsJsonObject().get("value").toString());
            tv_uv.setText(asset.attributes.get("uVIndex").getAsJsonObject().get("value").toString());
            tv_wea_data.setText(asset.attributes.get("weatherData").getAsJsonObject().get("value").getAsJsonObject().get("sys").getAsJsonObject().get("country").toString().replace("\"",""));
            tv_direc.setText(asset.attributes.get("windDirection").getAsJsonObject().get("value").toString());
            tv_speed.setText(asset.attributes.get("windSpeed").getAsJsonObject().get("value").toString() + " km/h");

            icon_id = asset.attributes.get("weatherData").getAsJsonObject().get("value").getAsJsonObject().get("weather").getAsJsonArray().get(0).getAsJsonObject().get("icon").toString().replace("\"","");
            icon_id = icon_id.substring(0,icon_id.length() -1);
            Log.d("aaa", icon_id);
        }

        switch (icon_id){
            case "01":
                img.setImageResource(R.drawable.ic_01d);
                break;
            case "02":
                img.setImageResource(R.drawable.ic_02d);
                break;
            case "03":
                img.setImageResource(R.drawable.ic_03d);
                break;
            case "04":
                img.setImageResource(R.drawable.ic_04d);
                break;
            case "50":
                img.setImageResource(R.drawable.ic_50d);
                break;
            case "09":
                img.setImageResource(R.drawable.ic_09d);
                break;
            case "10":
                img.setImageResource(R.drawable.ic_10d);
                break;
            case "11":
                img.setImageResource(R.drawable.ic_11d);
                break;
            case "13":
                img.setImageResource(R.drawable.ic_13d);
                break;
        }

    }
}