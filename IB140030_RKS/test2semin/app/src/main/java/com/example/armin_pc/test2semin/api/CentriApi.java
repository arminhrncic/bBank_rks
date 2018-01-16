package com.example.armin_pc.test2semin.api;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.armin_pc.test2semin.MyApp;
import com.example.armin_pc.test2semin.helper.Config;
import com.example.armin_pc.test2semin.helper.MyRunnable;
import com.example.armin_pc.test2semin.helper.volley.MyVolley;
import com.example.armin_pc.test2semin.model.bsp_Gradovi_SelectAll_Result;
import com.example.armin_pc.test2semin.model.bsp_TransfuzijskiCentri_SelectAll_Result;

/**
 * Created by Armin_pc on 27.11.2017.
 */

public class CentriApi {
    public static void ProvjeraCentri(final Context context, final MyRunnable<bsp_TransfuzijskiCentri_SelectAll_Result[]> onSuccess) {
        final String url = Config.url + "TransfuzijskiCentri/GetTransfuzijskiCentri";


        MyVolley.getWithoutParams(url, bsp_TransfuzijskiCentri_SelectAll_Result[].class,
                new Response.Listener<bsp_TransfuzijskiCentri_SelectAll_Result[]>() {
                    @Override
                    public void onResponse(bsp_TransfuzijskiCentri_SelectAll_Result[] response) {
                        onSuccess.run(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MyApp.getContext(), "Error server" + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
