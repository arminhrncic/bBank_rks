package com.example.armin_pc.test2semin.helper.url_connection;


import android.util.Log;

import com.example.armin_pc.test2semin.helper.MyGson;
import com.example.armin_pc.test2semin.model.bsp_Donatori_GetByEmailHCI_Result;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


import java.util.Arrays;
import java.util.List;

/**
 * Created by Armin_pc on 21.11.2017.
 */

public class HttpManager {

    public  static<T>  MojApiRezultat<T> responseGet (String url,Class<T> outputType,NameValuePair... inputParams)
    {
        String urlParam = URLEncodedUtils.format(Arrays.asList(inputParams),"utf-8");

        HttpGet httpGet = new HttpGet(url + "?" + urlParam);

        DefaultHttpClient client = new DefaultHttpClient();
        final  MojApiRezultat<T> rezultat = new MojApiRezultat<>();
        try {
           HttpResponse response =  client.execute(httpGet);

            InputStream stream = response.getEntity().getContent();


            String sJson= convertStreamToString(stream);

           // Gson gson = new Gson();
           T x = MyGson.build().fromJson(sJson,outputType);


            rezultat.isError = false;
            rezultat.value = x;




        } catch (IOException e) {
            Log.e("HttpManager",e.getMessage());
            rezultat.isError = true;
            rezultat.value = null;
            rezultat.errorMessage = e.getMessage();
        }
        return  rezultat;
    }

    public  static  String convertStreamToString (InputStream inputStream) throws IOException
    {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;

        while ( ((line = bufferedReader.readLine())) != null)
        {
            stringBuilder.append(line + "\n");
        }
        return  stringBuilder.toString();
    }

}
