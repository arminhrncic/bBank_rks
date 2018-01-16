package com.example.armin_pc.test2semin.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by Armin_pc on 25.11.2017.
 */

public class MyGson {

    public static Gson build(){

        GsonBuilder builder = new GsonBuilder();
        builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        return  builder.create();
    }

}
