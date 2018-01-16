package com.example.armin_pc.test2semin.api;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.armin_pc.test2semin.MainActivity;
import com.example.armin_pc.test2semin.MyApp;
import com.example.armin_pc.test2semin.helper.Config;
import com.example.armin_pc.test2semin.helper.MyRunnable;
import com.example.armin_pc.test2semin.helper.url_connection.HttpManager;
import com.example.armin_pc.test2semin.helper.url_connection.MojApiRezultat;
import com.example.armin_pc.test2semin.helper.volley.MyVolley;
import com.example.armin_pc.test2semin.model.bsp_Donatori_GetByEmailHCI_Result;
import com.example.armin_pc.test2semin.model.bsp_Donatori_GetByIdHCI_Result;
import com.google.gson.Gson;

import org.apache.http.message.BasicNameValuePair;

/**
 * Created by Armin_pc on 21.11.2017.
 */

public class LoginApi {



    public static void Provjera(final Context context, final String email, final MyRunnable<bsp_Donatori_GetByEmailHCI_Result> onSuccess) {
        final String url = Config.url + "Donatori/GetDonatoriByEmail";



        MyVolley.get(url, bsp_Donatori_GetByEmailHCI_Result.class, new Response.Listener<bsp_Donatori_GetByEmailHCI_Result>() {
            @Override
            public void onResponse(bsp_Donatori_GetByEmailHCI_Result response) {
                onSuccess.run(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MyApp.getContext(),"Error server" + error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }, new BasicNameValuePair("email",email));


    }


    public static void DodatniPodaci(final Context context, final String id, final MyRunnable<bsp_Donatori_GetByIdHCI_Result> onSuccess) {
        final String url = Config.url + "Donatori/GetDonatori";



        MyVolley.get(url, bsp_Donatori_GetByIdHCI_Result.class, new Response.Listener<bsp_Donatori_GetByIdHCI_Result>() {
            @Override
            public void onResponse(bsp_Donatori_GetByIdHCI_Result response) {
                onSuccess.run(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MyApp.getContext(),"Error server" + error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }, new BasicNameValuePair("id",id));


    }



}
