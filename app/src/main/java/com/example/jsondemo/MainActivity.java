package com.example.jsondemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1 ) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();

                }

                return  result;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);



            try {
                /*JSONArray jsonarray = new JSONArray(s);
                List<String> list = new ArrayList<String>();
                for(int i = 0; i < jsonarray.length(); i++) {
                    list.add(jsonarray.getJSONObject(i).getString("id_de_caso"));
                    Log.i("Número total de casos en Colombia", String.valueOf(list));*/


                        JSONArray jsonarray = new JSONArray(s);
                        for(int i = 0; i < jsonarray.length(); i++) {
                        JSONObject jsonPart = jsonarray.getJSONObject(i);

                        String total = jsonPart.getString("id_de_caso");
                        String fecha = jsonPart.getString("fecha_de_diagn_stico");
                        String ciudad = jsonPart.getString("ciudad_de_ubicaci_n");




                            Log.i("caso" + " " + (total) ,  "Fecha: " +  (fecha) + ", Ciudad: " + (ciudad) );
                            //Log.i("Fecha", jsonPart.getString("fecha_de_diagn_stico"));
                            //Log.i("Ciudad", jsonPart.getString("ciudad_de_ubicaci_n"));


                        }






            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadTask task = new DownloadTask();
        task.execute("https://www.datos.gov.co/resource/gt2j-8ykr.json?$order=id_de_caso%20DESC&$limit=2" );
    }
}
