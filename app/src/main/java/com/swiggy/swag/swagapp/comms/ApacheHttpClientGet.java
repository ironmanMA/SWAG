package com.swiggy.swag.swagapp.comms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class ApacheHttpClientGet {
	
	public static String base_url = "http://192.168.56.1:9200/cp/menu/_search?q=name:";
	
	public static void boxWinner(String json){
		
	}
	
	public static void elasticCall(Map<String,Integer> words){
		for(Map.Entry<String, Integer> each_word : words.entrySet()){
			// System.out.println(each_word.getKey() + "/" + each_word.getValue());
			try {
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpPost postRequest = new HttpPost(base_url+ URLEncoder.encode(each_word.getKey(), "UTF-8"));
				StringEntity input = new StringEntity("{}");
				input.setContentType("application/json");
				postRequest.setEntity(input);
				HttpResponse response = httpClient.execute(postRequest);
				BufferedReader br = new BufferedReader(
		                        new InputStreamReader((response.getEntity().getContent())));
				String output;
				System.out.println("Output from ES Server ...."+each_word.getKey());
				while ((output = br.readLine()) != null) {
//					JsonNode tree = objectMapper .readTree(output);
//					String formattedJson = objectMapper.writeValueAsString(tree);
					System.out.println(output+"\n");
				}
				httpClient.getConnectionManager().shutdown();
			  } catch (MalformedURLException e) {
				e.printStackTrace();
			  } catch (IOException e) {
				e.printStackTrace();
			  }
		}
	}
	public static void main(String[] args) {
		Map<String,Integer> json_data = new HashMap<String, Integer>();
		json_data.put("wrap", 10);
		json_data.put("chicken%20cheese", 20);
		json_data.put("paneer", 15);
		elasticCall(json_data);
	}
}
