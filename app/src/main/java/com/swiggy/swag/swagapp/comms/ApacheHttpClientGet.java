package com.swiggy.swag.swagapp.comms;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.swiggy.swag.swagapp.RecommendedDishResponseDAO;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApacheHttpClientGet {

    public static String base_url = "http://172.16.120.152:9200/swiggy/menu/_search?q=foodTitle:";

    public static String elasticSearchCall(String keyWord) {


        BufferedReader in = null;
        String data = null;

        try {
            HttpClient client = new DefaultHttpClient();

            URI website = new URI(base_url + URLEncoder.encode(keyWord, "UTF-8"));
            HttpGet request = new HttpGet();
            request.setURI(website);
            HttpResponse response = client.execute(request);
            response.getStatusLine().getStatusCode();

            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer sb = new StringBuffer("");
            String l = "";
            String nl = System.getProperty("line.separator");
            while ((l = in.readLine()) != null) {
                sb.append(l + nl);
            }
            in.close();
            data = sb.toString();
            return data;
        } catch (UnsupportedEncodingException e) {
            Log.e("HTTP-ERROR",e.getLocalizedMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("HTTP-ERROR",e.getLocalizedMessage());
            e.printStackTrace();
        } catch (URISyntaxException e) {
            Log.e("HTTP-ERROR",e.getLocalizedMessage());
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                    return data;
                } catch (Exception e) {
                    Log.e("GetMethodEx", e.getMessage());
                }
            }
        }
        return "";
    }

//    public static String elasticSearchCall(String keyWord) {
//            // System.out.println(each_word.getKey() + "/" + each_word.getValue());
//        if (android.os.Build.VERSION.SDK_INT > 9) {
//            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//            StrictMode.setThreadPolicy(policy);
//        }
//        BufferedReader in = null;
//        String data = null;
//            try {
//                HttpClient httpClient = new DefaultHttpClient();
//                URI website=null;
//                try {
//                    website = new URI(base_url + URLEncoder.encode(keyWord, "UTF-8"));
//                } catch (URISyntaxException e) {
//                    e.printStackTrace();
//                }
//                HttpGet request = new HttpGet();
//                request.setURI(website);
//                HttpResponse response = httpClient.execute(request);
//                response.getStatusLine().getStatusCode();
//
//                in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
//                StringBuffer sb = new StringBuffer("");
//                String l = "";
//                String nl = System.getProperty("line.separator");
//                while ((l = in.readLine()) != null) {
//                    sb.append(l + nl);
//                }
//                in.close();
//                data = sb.toString();
//                return data;
//                return output;
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        return null;
//    }

    public static ArrayList<RecommendedDishResponseDAO> esToDao(String httpResponse) {
        ArrayList<RecommendedDishResponseDAO> recommendedDishResponseDAOList = new ArrayList<>();
        if (httpResponse!=null && !httpResponse.isEmpty()){
            JsonParser parser = new JsonParser();
            Gson gson = new Gson();
            JsonObject jsonObject = parser.parse(httpResponse).getAsJsonObject();
            JsonArray jsonArray = jsonObject.getAsJsonObject("hits").getAsJsonArray("hits");
            for (JsonElement jsonElement : jsonArray) {
                JsonObject jsonObject2 = jsonElement.getAsJsonObject().getAsJsonObject("_source");
                RecommendedDishResponseDAO recommendedDishResponseDAO =
                        gson.fromJson(jsonObject2.toString(), RecommendedDishResponseDAO.class);
                recommendedDishResponseDAO.setLikenessScore(jsonElement.getAsJsonObject().get("_score").toString());
                recommendedDishResponseDAOList.add(recommendedDishResponseDAO);
            }
        }
        return recommendedDishResponseDAOList;
    }

    public static void main(String[] args) {
        Map<String, Integer> json_data = new HashMap<String, Integer>();
        String response= elasticSearchCall("chicken cheese");
        List<RecommendedDishResponseDAO> recommendedDishResponseDAOs = esToDao(response);
    }
}
