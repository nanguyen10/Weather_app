package com.example.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.project.Model.WeatherData;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Weather_Data";

    // Table Name
    private static final String TABLE_DATA = "data";

    // Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_TEMP = "temperature";
    private static final String KEY_HU_MI = "humidity";
    private static final String KEY_W_DIREC = "windDirection";
    private static final String KEY_W_SPEED = "windSpeed";
    private static final String KEY_TIME = "time";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DATA_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_DATA + "("
                + KEY_ID + " INTEGER," + KEY_NAME + " TEXT,"
                + KEY_TEMP + " FLOAT," + KEY_HU_MI + " FLOAT,"
                + KEY_W_DIREC + " FLOAT," + KEY_W_SPEED + " FLOAT,"
                + KEY_TIME + " TEXT" +")";
        db.execSQL(CREATE_DATA_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       /* db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA);

        // Create tables again
        onCreate(db);*/

    }

    public void addWeatherData(WeatherData weatherData) {
        SQLiteDatabase db = this.getWritableDatabase();
        String nullColumnHack = null; // Allow null value
        ContentValues values = new ContentValues();
        if (weatherData.getId() != null) {
            values.put(KEY_ID, weatherData.getId());
            values.put(KEY_NAME, weatherData.getName());
            values.put(KEY_HU_MI, weatherData.getHumidity());
            values.put(KEY_TEMP, weatherData.getTemperature());
            values.put(KEY_W_DIREC, weatherData.getWindDirection());
            values.put(KEY_W_SPEED, weatherData.getWindSpeed());
            values.put(KEY_TIME, weatherData.getTime());

            db.insert(TABLE_DATA,nullColumnHack,values);
        }
        db.close();

    }

    public WeatherData getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] fields = {KEY_ID, KEY_NAME, KEY_TEMP, KEY_HU_MI, KEY_W_DIREC, KEY_W_SPEED, KEY_TIME};
        String criterials = KEY_ID + "=?";
        String[] parameters = {String.valueOf(id)};
        String groupby = null;
        String having = null;
        String orderby = null;
        Cursor cursor = db.query(TABLE_DATA, fields, criterials,
                parameters, groupby, having, orderby);
        if (cursor != null) cursor.moveToFirst();
        WeatherData weatherData = new WeatherData(cursor.getString(0),
                cursor.getString(1), Float.parseFloat(cursor.getString(2)),
                Float.parseFloat(cursor.getString(3)), Float.parseFloat(cursor.getString(4)),
                Float.parseFloat(cursor.getString(5)), cursor.getString(6));
        return weatherData;


    }

    public List<WeatherData> getAllData() {
        List<WeatherData>  weatherDataList = new ArrayList<WeatherData>();
        String[] criterial = null;
        String selectQuery = "SELECT  * FROM " + TABLE_DATA;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, criterial);
        if (cursor.moveToFirst()) {
            do {
                WeatherData weatherData = new WeatherData();
                weatherData.setId(cursor.getString(0));
                weatherData.setName(cursor.getString(1));
                weatherData.setTemperature(Float.parseFloat(cursor.getString(2)));
                weatherData.setHumidity(Float.parseFloat(cursor.getString(3)));
                weatherData.setWindDirection(Float.parseFloat(cursor.getString(4)));
                weatherData.setWindSpeed(Float.parseFloat(cursor.getString(5)));
                weatherData.setTime(cursor.getString(6));
                // Add student to list
                weatherDataList.add(weatherData);
            } while (cursor.moveToNext());
        }
        return weatherDataList;

    }

    public List<WeatherData> getDataFrom(String name) {
        List<WeatherData>  weatherDataList = new ArrayList<WeatherData>();
        String[] criterial = null;
        String selectQuery = "SELECT  * FROM " + TABLE_DATA + " WHERE " + KEY_NAME +" = " + "'" + name +"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, criterial);
        if (cursor.moveToFirst()) {
            do {
                WeatherData weatherData = new WeatherData();
                weatherData.setId(cursor.getString(0));
                weatherData.setName(cursor.getString(1));
                weatherData.setTemperature(Float.parseFloat(cursor.getString(2)));
                weatherData.setHumidity(Float.parseFloat(cursor.getString(3)));
                weatherData.setWindDirection(Float.parseFloat(cursor.getString(4)));
                weatherData.setWindSpeed(Float.parseFloat(cursor.getString(5)));
                weatherData.setTime(cursor.getString(6));
                // Add student to list
                weatherDataList.add(weatherData);
            } while (cursor.moveToNext());
        }
        return weatherDataList;

    }

    /*// Updating single contact
    public int updateContact(WeatherAsset contact) {}

    // Deleting single contact
    public void deleteContact(WeatherAsset contact) {}*/

}
