package com.example.armin_pc.test2semin.api;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.armin_pc.test2semin.MyApp;
import com.example.armin_pc.test2semin.helper.Config;
import com.example.armin_pc.test2semin.helper.MyRunnable;
import com.example.armin_pc.test2semin.helper.volley.MyVolley;
import com.example.armin_pc.test2semin.model.ZahtjevZaKrv;
import com.example.armin_pc.test2semin.model.bsp_Donatori_GetByEmailHCI_Result;
import com.example.armin_pc.test2semin.model.bsp_TransfuzijskiCentri_SelectAll_Result;
import com.example.armin_pc.test2semin.model.bsp_Zahtjevi_GetByDonatorId_Result;

import org.apache.http.message.BasicNameValuePair;

/**
 * Created by Armin_pc on 27.11.2017.
 */

public class ZahtjevApi {

    public static void ProvjeraZahtjevi(final Context context,final String id, final MyRunnable<bsp_Zahtjevi_GetByDonatorId_Result[]> onSuccess) {
        final String url = Config.url + "ZahtjevZaKrv/GetZahtjevZaKrvByDonatorId/";

        MyVolley.get(url, bsp_Zahtjevi_GetByDonatorId_Result[].class, new Response.Listener<bsp_Zahtjevi_GetByDonatorId_Result[]>() {
            @Override
            public void onResponse(bsp_Zahtjevi_GetByDonatorId_Result[] response) {
                onSuccess.run(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MyApp.getContext(),"Error server" + error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }, new BasicNameValuePair("id",id));


    }

    public  static  void SlanjeZahtjeva (final Context context, final ZahtjevZaKrv zahtjev,final MyRunnable<ZahtjevZaKrv> onSuccess)
    {
        final String url = Config.url + "ZahtjevZaKrv/PostZahtjevZaKrv/";

        MyVolley.post(url, ZahtjevZaKrv.class, new Response.Listener<ZahtjevZaKrv>() {
            @Override
            public void onResponse(ZahtjevZaKrv response) {
                onSuccess.run(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MyApp.getContext(),"Error server" + error.getMessage(),Toast.LENGTH_LONG).show();

            }
        }, zahtjev);
    }



}
