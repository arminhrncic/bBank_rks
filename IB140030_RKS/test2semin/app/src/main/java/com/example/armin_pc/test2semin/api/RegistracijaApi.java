package com.example.armin_pc.test2semin.api;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.armin_pc.test2semin.MyApp;
import com.example.armin_pc.test2semin.helper.Config;
import com.example.armin_pc.test2semin.helper.MyRunnable;
import com.example.armin_pc.test2semin.helper.volley.MyVolley;
import com.example.armin_pc.test2semin.model.Donacije;
import com.example.armin_pc.test2semin.model.Donatori;

/**
 * Created by Armin_pc on 30.11.2017.
 */

public class RegistracijaApi {

    public  static  void RegistracijaDonatora (final Context context, final Donatori donator, final MyRunnable<Donatori> onSuccess)
    {
        final String url = Config.url + "Donatori/PostDonatori/";

        MyVolley.post(url, Donatori.class, new Response.Listener<Donatori>() {
            @Override
            public void onResponse(Donatori response) {
                onSuccess.run(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MyApp.getContext(),"Error server" + error.getMessage(),Toast.LENGTH_LONG).show();

            }
        }, donator);
    }



}
