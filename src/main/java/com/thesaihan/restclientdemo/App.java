package com.thesaihan.restclientdemo;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Hello world!
 *
 */
public class App 
{
    private static OkHttpClient client = new OkHttpClient();
    public static void main( String[] args )
    {
        System.out.println("Trello List");
        System.out.println("-----------");
        String listStr = getJsonStringFrom("https://trello-clone-ppm.herokuapp.com/list");
        try {
            List<Map<String, Object>> list = new ObjectMapper().readValue(listStr, List.class);
            for (Map<String,Object> map : list) {
                System.out.println(map.get("id").toString() +" :: " + map.get("title").toString());
                List<Map<String, Object>> cards = (List) map.get("cards");
                for (Map<String,Object> card : cards) {
                    System.out.println("\t* "+card.get("title").toString());
                }
            }
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
    }

    public static String getJsonStringFrom(String url) {
        Request request = new Request.Builder()
            .url(url)
            .build();

        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch(IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
