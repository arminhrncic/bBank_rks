package com.example.armin_pc.test2semin.api;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.armin_pc.test2semin.MyApp;
import com.example.armin_pc.test2semin.helper.Config;
import com.example.armin_pc.test2semin.helper.MyRunnable;
import com.example.armin_pc.test2semin.helper.url_connection.HttpManager;
import com.example.armin_pc.test2semin.helper.url_connection.MojApiRezultat;
import com.example.armin_pc.test2semin.helper.volley.MyVolley;
import com.example.armin_pc.test2semin.model.bsp_Gradovi_SelectAll_Result;

import java.util.List;

/**
 * Created by Armin_pc on 27.11.2017.
 */

public class GradoviApi {
    public static void ProvjeraGrada(final Context context, final MyRunnable<bsp_Gradovi_SelectAll_Result[]> onSuccess) {
        final String url = Config.url + "Gradovi/GetGradovi";




        MyVolley.getWithoutParams(url,bsp_Gradovi_SelectAll_Result[].class,
                new Response.Listener<bsp_Gradovi_SelectAll_Result[]>() {
            @Override
            public void onResponse(bsp_Gradovi_SelectAll_Result[] response) {
                onSuccess.run(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MyApp.getContext(),"Error server" + error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });



    }


}
