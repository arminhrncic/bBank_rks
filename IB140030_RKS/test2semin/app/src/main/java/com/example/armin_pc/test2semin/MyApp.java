package com.example.armin_pc.test2semin;

import android.app.Application;
import android.content.Context;

/**
 * Created by Armin_pc on 26.11.2017.
 */

public class MyApp extends Application
{

    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
