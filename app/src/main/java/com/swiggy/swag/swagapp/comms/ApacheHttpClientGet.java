package com.swiggy.swag.swagapp.comms;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.swiggy.swag.swagapp.RecommendedDishResponseDAO;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApacheHttpClientGet {

    public static String base_url = "http://172.16.120.152:9200/swag/menu/_search?q=name:";

    public static void boxWinner(String json) {

    }

    public static String elasticSearchCall(Map<String, Integer> words) {
        for (Map.Entry<String, Integer> each_word : words.entrySet()) {
            // System.out.println(each_word.getKey() + "/" + each_word.getValue());
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost postRequest = new HttpPost(base_url + URLEncoder.encode(each_word.getKey(), "UTF-8"));
                StringEntity input = new StringEntity("{}");
                input.setContentType("application/json");
                postRequest.setEntity(input);
                HttpResponse response = httpClient.execute(postRequest);
                BufferedReader br = new BufferedReader(
                        new InputStreamReader((response.getEntity().getContent())));
                String output;
                System.out.println("Output from ES Server ...." + each_word.getKey());
                while ((output = br.readLine()) != null) {
                    System.out.println(output + "\n");
                }
                httpClient.getConnectionManager().shutdown();
                return output;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static List<RecommendedDishResponseDAO> esToDao(String httpResponse) {
        List<RecommendedDishResponseDAO> recommendedDishResponseDAOList = new ArrayList<>();
        JsonParser parser = new JsonParser();
        Gson gson = new Gson();
        JsonObject jsonObject = parser.parse(httpResponse).getAsJsonObject();
        JsonArray jsonArray = jsonObject.getAsJsonObject("hits").getAsJsonArray("hits");
        for (JsonElement jsonElement : jsonArray) {
            JsonObject jsonObject2 = jsonElement.getAsJsonObject().getAsJsonObject("_source");
            RecommendedDishResponseDAO recommendedDishResponseDAO =
                    gson.fromJson(jsonObject2.toString(), RecommendedDishResponseDAO.class);
            recommendedDishResponseDAOList.add(recommendedDishResponseDAO);
        }

        return recommendedDishResponseDAOList;
    }

    public static void main(String[] args) {
        Map<String, Integer> json_data = new HashMap<String, Integer>();
//        json_data.put("wrap", 10);
        json_data.put("chicken cheese", 20);
//        json_data.put("paneer", 15);
        String response= elasticSearchCall(json_data);
        List<RecommendedDishResponseDAO> recommendedDishResponseDAOs = esToDao(response);
    }
}
