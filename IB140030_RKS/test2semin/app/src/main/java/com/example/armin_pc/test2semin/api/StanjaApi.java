package com.example.armin_pc.test2semin.api;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.armin_pc.test2semin.MyApp;
import com.example.armin_pc.test2semin.helper.Config;
import com.example.armin_pc.test2semin.helper.MyRunnable;
import com.example.armin_pc.test2semin.helper.volley.MyVolley;
import com.example.armin_pc.test2semin.model.bsp_Donacije_GetByDonatorIdHCI_Result;
import com.example.armin_pc.test2semin.model.bsp_Stanja_SelectByCentarId_Result;

import org.apache.http.message.BasicNameValuePair;

/**
 * Created by Armin_pc on 30.11.2017.
 */

public class StanjaApi {
    public static void ProvjeraStanja (final Context context, final String centarId, final MyRunnable<bsp_Stanja_SelectByCentarId_Result[]> onSuccess) {
        final String url = Config.url + "Stanja/GetStanjaByCentar";

        MyVolley.get(url, bsp_Stanja_SelectByCentarId_Result[].class, new Response.Listener<bsp_Stanja_SelectByCentarId_Result[]>() {
            @Override
            public void onResponse(bsp_Stanja_SelectByCentarId_Result[] response) {
                onSuccess.run(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MyApp.getContext(), "Error server " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }, new BasicNameValuePair("centarId", centarId));

    }
}
