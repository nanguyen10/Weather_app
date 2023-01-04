package com.example.project;

import static java.time.LocalDate.*;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.time.LocalDate;


public class OtherFragment extends Fragment {

    Button btn_get;
    LinearLayout layout;
    View view;
    EditText edt_city;
    ImageView img;
    TextView date, description, place, humidity, temperature, wDirection,wSpeed, tv_response;

    private final String url = "https://api.openweathermap.org/data/2.5/weather";
    private final String openWeatherKey = "1713ee2690c5f70e2ada3da2aae253fc";
    DecimalFormat df = new DecimalFormat("#.##");
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_other, container, false);
        anhxa();
        btn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String URL ="";
                String city = edt_city.getText().toString().trim();
                if(city.equals("")){
                    tv_response.setVisibility(View.VISIBLE);
                    tv_response.setText("Please enter a city. That field can not be empty!");
                }
                else {
                    URL =url + "?q=" + city + "&appid=" + openWeatherKey;
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                tv_response.setVisibility(View.GONE);
                                layout.setVisibility(View.VISIBLE);
                                JSONObject Response = new JSONObject(response);
                                JSONObject Weather = Response.getJSONArray("weather").getJSONObject(0);
                                JSONObject Main = Response.getJSONObject("main");
                                JSONObject Wind = Response.getJSONObject("wind");
                                JSONObject Sys = Response.getJSONObject("sys");


                                date.setText(String.valueOf(LocalDate.now()));
                                place.setText(Response.getString("name") + " (" + Sys.getString("country") + ")");
                                description.setText(Weather.getString("description")
                                        + "\nFeels like "+ String.valueOf(df.format(Main.getDouble("feels_like") - 273.1)) + " °C");
                                temperature.setText(String.valueOf(df.format(Main.getDouble("temp") - 273.15)));
                                humidity.setText(String.valueOf(Main.getInt("humidity")));
                                wSpeed.setText(String.valueOf(Wind.getString("speed")));
                                wDirection.setText(String.valueOf(Wind.getString("deg")));

                                String icon_id = Weather.getString("icon");
                                icon_id= icon_id.substring(0,icon_id.length()-1);

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


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            layout.setVisibility(View.GONE);
                            tv_response.setVisibility(View.VISIBLE);
                            tv_response.setText("Can not find the city!");

                        }
                    });
                    RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                    requestQueue.add(stringRequest);

                }
            }
        });

        return view;
    }

    private void anhxa() {
        btn_get= (Button) view.findViewById(R.id.btn_get);
        edt_city = (EditText) view.findViewById(R.id.edt_city);
        img = (ImageView) view.findViewById(R.id.img_icon);
        date= (TextView) view.findViewById(R.id.tv_date_val);
        description= (TextView) view.findViewById(R.id.tv_description);
        place= (TextView) view.findViewById(R.id.tv_place);
        humidity= (TextView) view.findViewById(R.id.tv_humidity_val);
        temperature= (TextView) view.findViewById(R.id.tv_temperature_val);
        wDirection= (TextView) view.findViewById(R.id.tv_wind_direction_val);
        wSpeed= (TextView) view.findViewById(R.id.tv_wind_speed_val);
        tv_response = (TextView) view.findViewById(R.id.tv_response);
        layout = (LinearLayout) view.findViewById(R.id.layout);
    }
}