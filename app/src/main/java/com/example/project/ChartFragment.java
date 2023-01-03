package com.example.project;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
    Spinner sp1,sp2,sp0,sp3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart,container,false);

        mainActivity3 = (MainActivity3) getActivity();

        DatabaseHandler db = new DatabaseHandler(mainActivity3);
        //addData();

        String[] spn = new String[4];


        sp0 = view.findViewById(R.id.sp_assetName);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(mainActivity3,R.array.asset, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp0.setAdapter(adapter1);
        sp0.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spn[0] = String.valueOf(parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp1 = view.findViewById(R.id.sp_attribute);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(mainActivity3,R.array.attributes,android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(adapter2);
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spn[1] = String.valueOf(parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp2 = view.findViewById(R.id.sp_month);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(mainActivity3,R.array.month,android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp2.setAdapter(adapter3);
        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spn[2] = String.valueOf(position+1);
                if (position<10){
                    spn[2] = "0"+ spn[2];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp3 = view.findViewById(R.id.sp_year);
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(mainActivity3,R.array.year,android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp3.setAdapter(adapter4);
        sp3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spn[3] = String.valueOf(parent.getItemAtPosition(position));
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

                if(db.getDataFrom(spn[0])!= null) {
                    String d = "";
                    Boolean f = true;
                    for (WeatherData data : db.getDataFrom(spn[0])) {
                        String[] ymd = data.getTime().split("T")[0].split("-");
                        if(ymd[1].equals(spn[2]) && ymd[0].equals(spn[3])){
                            if(f){
                                d = ymd[2];
                                data.setTime(d);
                                weatherData.add(data);
                                f=false;
                            }
                            else {
                                if(!ymd[2].equals(d)){
                                    d = ymd[2];
                                    data.setTime(d);
                                    weatherData.add(data);
                                }
                            }

                        }
                    }


                }
                String All=new String();
                graph.refreshDrawableState();
                graph.removeAllSeries();
                LineGraphSeries series = new LineGraphSeries<DataPoint>();

                switch (spn[1]){
                    case "humidity":
                        for (WeatherData data: weatherData) {
                            All += "Humidity: " + data.humidity + " \t" + "(day: " +data.time + ")\n";
                            series.appendData(new DataPoint(Integer.parseInt(data.time), data.humidity),true,31);
                        }
                        break;
                    case "temperature":
                        for (WeatherData data: weatherData) {
                            All += "Temperature: " + data.temperature + " \t" + "(day: " +data.time + ")\n";
                            series.appendData(new DataPoint(Integer.parseInt(data.time), data.temperature),true,31);
                        }
                        break;

                    case "windDirection":
                        for (WeatherData data: weatherData) {
                            All += "Wind Direction: " + data.windDirection + " \t" + "(day: " +data.time + ")\n";
                            series.appendData(new DataPoint(Integer.parseInt(data.time), data.windDirection),true,31);
                        }
                        break;
                    case "windSpeed":
                        for (WeatherData data: weatherData) {
                            All += "Wind Speed: " + data.windSpeed + " \t" + "(day: " +data.time + ")\n";
                            series.appendData(new DataPoint(Integer.parseInt(data.time), data.windSpeed),true,31);
                        }
                        break;

                }

                series.setColor(Color.rgb(255,0,0));
                series.setThickness(5);
                series.setDrawDataPoints(true);
                series.setDrawBackground(true);
                series.setBackgroundColor(Color.argb(60,0,255,255));
                series.setDataPointsRadius(10);

                graph.getViewport().setScrollable(true);
                graph.addSeries(series);
                graph.getViewport().setScalable(true);

                graph.getViewport().setMaxX(31);
                graph.getViewport().setMinX(1);
//                graph.getViewport().setXAxisBoundsManual(true);
//                graph.getViewport().setMaxXAxisSize(graph.getViewport().getMaxXAxisSize());
//                graph.getViewport().setMinX(graph.getViewport().getMinX(true));

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

