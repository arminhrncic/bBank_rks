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
import com.example.armin_pc.test2semin.model.ZahtjevZaKrv;
import com.example.armin_pc.test2semin.model.bsp_Donacije_GetByDonatorIdHCI_Result;
import com.example.armin_pc.test2semin.model.bsp_Zahtjevi_GetByDonatorId_Result;

import org.apache.http.message.BasicNameValuePair;

/**
 * Created by Armin_pc on 28.11.2017.
 */

public class DonacijeApi {

    public static void ProvjeraDonacije (final Context context, final String donatorId, final MyRunnable<bsp_Donacije_GetByDonatorIdHCI_Result[]> onSuccess) {
        final String url = Config.url + "Donacije/GetDonacijeByDonator";

        MyVolley.get(url, bsp_Donacije_GetByDonatorIdHCI_Result[].class, new Response.Listener<bsp_Donacije_GetByDonatorIdHCI_Result[]>() {
            @Override
            public void onResponse(bsp_Donacije_GetByDonatorIdHCI_Result[] response) {
                onSuccess.run(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MyApp.getContext(), "Error server " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }, new BasicNameValuePair("donatorId", donatorId));

    }


    public  static  void SlanjeDonacije (final Context context, final Donacije donacija, final MyRunnable<Donacije> onSuccess)
    {
        final String url = Config.url + "Donacije/PostDonacije/";

        MyVolley.post(url, Donacije.class, new Response.Listener<Donacije>() {
            @Override
            public void onResponse(Donacije response) {
                onSuccess.run(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MyApp.getContext(),"Error server" + error.getMessage(),Toast.LENGTH_LONG).show();

            }
        }, donacija);
    }


}
