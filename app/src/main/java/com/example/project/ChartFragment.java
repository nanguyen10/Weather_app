package com.example.project;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.project.Model.Asset;
import com.example.project.Model.ListAsset;
import com.example.project.Model.WeatherData;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ChartFragment extends Fragment {

    private MainActivity3 mainActivity3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart,container,false);

        mainActivity3 = (MainActivity3) getActivity();

        DatabaseHandler db = new DatabaseHandler(mainActivity3);
        //addData();

        String[] asset_name = new String[1];

        String[] asset_attribute = new String[1];

        Spinner[] sp1 = {view.findViewById(R.id.sp_assetName)};
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(mainActivity3,R.array.asset, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1[0].setAdapter(adapter1);
        sp1[0].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                asset_name[0] = String.valueOf(parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner sp2 = view.findViewById(R.id.sp_attribute);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(mainActivity3,R.array.attributes,android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp2.setAdapter(adapter2);
        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                asset_attribute[0] = String.valueOf(parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        GraphView graph =  view.findViewById(R.id.graph_view);
        Button btn = view.findViewById(R.id.btn_show);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<WeatherData> weatherData = new ArrayList<>();
                String[] day = db.getDataFrom(asset_name[0]).get(0).getTime().split("T")[0].split("-");
                String d = day[2];
                weatherData.add(db.getDataFrom(asset_name[0]).get(0));
                weatherData.get(0).setTime(d);

                for (WeatherData data : db.getAllData()) {
                    String[] day_i = data.getTime().split("T")[0].split("-");
                    String d_i = day_i[2];
                    if (!d_i.equals(d)) {
                        data.setTime(d_i);
                        weatherData.add(data);
                        d = d_i;
                    }
                }
                String All=new String();

                graph.removeAllSeries();
                LineGraphSeries series = new LineGraphSeries<DataPoint>();

                switch (asset_attribute[0]){
                    case "humidity":
                        for (WeatherData data: weatherData) {
                            All += "Humidity: " + data.humidity + " " + " day: " +data.time + "\n";
                            series.appendData(new DataPoint(Integer.parseInt(data.time), data.humidity),true,10);
                        }
                        break;
                    case "temperature":
                        for (WeatherData data: weatherData) {
                            All += "Temperature: " + data.temperature + " " + " day: " +data.time + "\n";
                            series.appendData(new DataPoint(Integer.parseInt(data.time), data.temperature),true,10);
                        }
                        break;

                    case "windDirection":
                        for (WeatherData data: weatherData) {
                            All += "Wind Direction: " + data.windDirection + " " + " day: " +data.time + "\n";
                            series.appendData(new DataPoint(Integer.parseInt(data.time), data.windDirection),true,10);
                        }
                        break;
                    case "windSpeed":
                        for (WeatherData data: weatherData) {
                            All += "Wind Speed: " + data.windSpeed + " " + " day: " +data.time + "\n";
                            series.appendData(new DataPoint(Integer.parseInt(data.time), data.windSpeed),true,10);
                        }
                        break;

                }

                //series.setTitle(asset_attribute[0]);
                series.setColor(Color.rgb(255,0,0));
                series.setThickness(6);
                series.setDrawDataPoints(true);
                series.setDrawBackground(true);
                series.setBackgroundColor(Color.argb(65,0,150,0));
                series.setDataPointsRadius(15);

                graph.addSeries(series);

                TextView tv = view.findViewById(R.id.tv);
                tv.setText(All);

            }
        });



        return view;
    }

    public void addData() {
        DatabaseHandler db = new DatabaseHandler(mainActivity3);
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

