package com.example.project.Model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.Serializable;


public class Map implements Serializable {
    public JsonObject options;
    public JsonObject defaultt;
    public JsonArray center;
    public JsonArray bounds;


}
