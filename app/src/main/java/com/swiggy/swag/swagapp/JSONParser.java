package com.swiggy.swag.swagapp;

/**
 * Created by gaurav on 7/16/17.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;


import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

class JSONParser {

    InputStream is = null;
    JSONObject jObj = null;
    String json = "";
    public JSONParser() {}
    public String getJSONFromUrl(String urlstr) {
        try {
            URL url = new URL(urlstr);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            is = urlConnection.getInputStream();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);

            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
            System.out.println("sb:"+sb);
            Log.e("DATA","Here is data "+sb);
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        return sb.toString();
    }
    public String getStringFromStream(InputStream is){
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);

            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
            System.out.println("sb:"+sb);
            Log.e("DATA","Here is data "+sb);
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        return sb.toString();
    }
}