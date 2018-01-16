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
import com.example.armin_pc.test2semin.model.bsp_Donacije_GetByDonatorIdHCI_Result;
import com.example.armin_pc.test2semin.model.bsp_Donatori_GetByKrvnaGrupaIndexHCI_Result;

import org.apache.http.message.BasicNameValuePair;

/**
 * Created by Armin_pc on 28.11.2017.
 */

public class DonatoriApi {

    public static void PretragaDonatora (final Context context, final String id, final MyRunnable<bsp_Donatori_GetByKrvnaGrupaIndexHCI_Result[]> onSuccess) {
        final String url = Config.url + "Donatori/GetDonatoriByKrvnaGrupaIndex";

        MyVolley.get(url, bsp_Donatori_GetByKrvnaGrupaIndexHCI_Result[].class, new Response.Listener<bsp_Donatori_GetByKrvnaGrupaIndexHCI_Result[]>() {
            @Override
            public void onResponse(bsp_Donatori_GetByKrvnaGrupaIndexHCI_Result[] response) {
                onSuccess.run(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MyApp.getContext(), "Error server " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }, new BasicNameValuePair("id", id));

    }


    public  static  void IzmjenaPodataka (final Context context, String id, final Donatori donatori, final MyRunnable<Donatori> onSuccess)
    {
        final String url = Config.url + "Donatori/PutDonatori/";

        MyVolley.put(url, Donatori.class, new Response.Listener<Donatori>() {
            @Override
            public void onResponse(Donatori response) {
                onSuccess.run(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MyApp.getContext(),"Error server" + error.getMessage(),Toast.LENGTH_LONG).show();

            }
        }, donatori,new BasicNameValuePair("id", id));
    }


    public  static  void IzmjenaSlike (final Context context, String id, final Donatori donatori, final MyRunnable<Donatori> onSuccess)
    {
        final String url = Config.url + "Donatori/PutDonatoriSlika/";

        MyVolley.put(url, Donatori.class, new Response.Listener<Donatori>() {
            @Override
            public void onResponse(Donatori response) {
                onSuccess.run(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MyApp.getContext(),"Error server" + error.getMessage(),Toast.LENGTH_LONG).show();

            }
        }, donatori,new BasicNameValuePair("id", id));
    }

}
