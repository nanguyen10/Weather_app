package com.example.project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.project.Model.Asset;
import com.example.project.Model.ListAsset;

public class DetailFragment extends Fragment {

    View view;
    TextView tv_asset,tv_lat,tv_lon,tv_humidity,tv_rain,tv_altitude,tv_azi,tv_irradiance,tv_zenith,tv_temp,tv_uv,tv_wea_data,tv_direc,tv_speed;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_detail,container,false);

        anhxa();
        initUI();
        return view;
    }

    private void anhxa() {
        tv_asset= (TextView) view.findViewById(R.id.tv_asset_name);
        tv_lat= (TextView) view.findViewById(R.id.tv_lat);
        tv_lon= (TextView) view.findViewById(R.id.tv_lon);
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
        Asset asset = ListAsset.assett;
        if(asset != null){
            tv_asset.setText(asset.name);
            tv_lat.setText(asset.coordinates.get(1).toString());
            tv_lon.setText(asset.coordinates.get(0).toString());
            tv_humidity.setText(asset.attributes.get("humidity").getAsJsonObject().get("value").toString());
            tv_rain.setText(asset.attributes.get("rainfall").getAsJsonObject().get("value").toString());
            tv_altitude.setText(asset.attributes.get("rainfall").getAsJsonObject().get("value").toString());
            tv_azi.setText(asset.attributes.get("sunAzimuth").getAsJsonObject().get("value").toString());
            tv_irradiance.setText(asset.attributes.get("sunIrradiance").getAsJsonObject().get("value").toString());
            tv_zenith.setText(asset.attributes.get("sunZenith").getAsJsonObject().get("value").toString());
            tv_temp.setText(asset.attributes.get("temperature").getAsJsonObject().get("value").toString());
            tv_uv.setText(asset.attributes.get("uVIndex").getAsJsonObject().get("value").toString());
            tv_wea_data.setText("null");
            tv_direc.setText(asset.attributes.get("windDirection").getAsJsonObject().get("value").toString());
            tv_speed.setText(asset.attributes.get("windSpeed").getAsJsonObject().get("value").toString() + " km/h");
        }


    }
}