package com.example.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class InfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    Context context;

    public InfoWindowAdapter(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View getInfoContents(@NonNull Marker marker) {
        return null;
    }

    @Nullable
    @Override
    public View getInfoWindow(@NonNull Marker marker) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_infowindow,null);
        TextView tv_Name = view.findViewById(R.id.tv_name);
        TextView tv_Lat = view.findViewById(R.id.tv_lat);
        TextView tv_Lon = view.findViewById(R.id.tv_lon);
        tv_Name.setText(marker.getTitle());
        LatLng lt = marker.getPosition();
        tv_Lat.setText("Latitude: " + lt.latitude);
        tv_Lon.setText("Longitude: " + lt.longitude);

        return view;
    }
}
