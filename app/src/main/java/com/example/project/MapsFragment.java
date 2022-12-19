package com.example.project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.project.Model.Asset;
import com.example.project.Model.ListAsset;
import com.example.project.Model.Map;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsFragment extends Fragment{

    private MainActivity3 mainActivity3;
    APIInterface apiInterface;
    BottomNavigationView navigationView;
    private GoogleMap mMap;
    Float zoom,max_zoom,min_zoom;
    ArrayList<Asset> weatherAsset= new ArrayList<>();
    ArrayList<Asset> respondAsset= new ArrayList<>();
    Map map = new Map();

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {

            mMap = googleMap;

            for (Asset asset: ListAsset.list) {
                if(asset.type.equals("WeatherAsset")){
                    asset.coordinates = asset.attributes.getAsJsonObject().get("location").getAsJsonObject().get("value").getAsJsonObject().get("coordinates").getAsJsonArray();
                    weatherAsset.add(asset);
                    LatLng weather = new LatLng(Double.parseDouble(String.valueOf(asset.coordinates.get(1))), Double.parseDouble(String.valueOf(asset.coordinates.get(0))));
                    mMap.addMarker(new MarkerOptions().position(weather).title(asset.name));
                }

            }
            //ListAsset.list = weatherAsset;

            if(mMap != null){
                mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                    @Nullable
                    @Override
                    public View getInfoContents(@NonNull Marker marker) {
                        return null;
                    }

                    @Nullable
                    @Override
                    public View getInfoWindow(@NonNull Marker marker) {
                        View view = getLayoutInflater().inflate(R.layout.custom_infowindow,null);
                        return view;
                    }
                });
            }
            mMap.setInfoWindowAdapter(new InfoWindowAdapter(mainActivity3));

            LatLng uit = new LatLng(Double.parseDouble(String.valueOf(map.center.get(1))) + 0.00021 ,
                                    Double.parseDouble(String.valueOf(map.center.get(0))) + 0.0029);

            googleMap.moveCamera(CameraUpdateFactory.newLatLng(uit));

            mMap.setMinZoomPreference(min_zoom);
            mMap.setMaxZoomPreference(max_zoom);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(uit,zoom + 2));

            LatLngBounds UitBounds = new LatLngBounds(
                    new LatLng( Double.parseDouble(String.valueOf(map.bounds.get(1))), Double.parseDouble(String.valueOf(map.bounds.get(0)))),
                    new LatLng(Double.parseDouble(String.valueOf(map.bounds.get(3))), Double.parseDouble(String.valueOf(map.bounds.get(2))))
            );
            mMap.setLatLngBoundsForCameraTarget(UitBounds);
            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(@NonNull Marker marker) {
                    for (Asset asset: weatherAsset) {
                        if(marker.getTitle().equals(asset.name)) {
                            ListAsset.assett = asset;
                        }
                    }
                    navigationView = mainActivity3.navigationView;
                    navigationView.setSelectedItemId(R.id.action_detail);

                }
            });

        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        
        mainActivity3 = (MainActivity3) getActivity();


        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<Map> call = apiInterface.getMap();
        call.enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                Log.d("API CALL", response.code()+"");
                map = response.body();
                map.defaultt = map.options.getAsJsonObject().get("default").getAsJsonObject();
                map.center = map.defaultt.get("center").getAsJsonArray();
                map.bounds = map.defaultt.get("bounds").getAsJsonArray();
                zoom= Float.parseFloat(String.valueOf(map.defaultt.get("zoom")));
                max_zoom= Float.parseFloat(String.valueOf(map.defaultt.get("maxZoom")));
                min_zoom= Float.parseFloat(String.valueOf(map.defaultt.get("minZoom")));
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                Log.d("API CALL", t.getMessage().toString());
            }

        });
        apiInterface = APIClient.getClient().create(APIInterface.class);



        return inflater.inflate(R.layout.fragment_maps, container, false);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
                mapFragment.getMapAsync(callback);
        }

    }

}