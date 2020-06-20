package com.thesaihan.restclientdemo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        // processCreate();
        processRead();
        // processUpdate();
        // processDelete();
    }

	private static void processCreate() {
        Map<String, Object> myNewList = new HashMap<String,Object>();
        myNewList.put("title", "Another DK Card");
        myNewList.put("position", 8);
        myNewList.put("status", 1);
        ListDao.create("/list", myNewList);
    }

    private static void processRead() {
        System.out.println("Trello List");
        System.out.println("-----------");
        String listStr = ListDao.read("/list");
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

    private static void processUpdate() {
        Map<String, Object> listToBeUpdated = new HashMap<String,Object>();
        listToBeUpdated.put("title", "List created by DK team");
        listToBeUpdated.put("position", 7);
        listToBeUpdated.put("status", 1);
        ListDao.update("/list", 47, listToBeUpdated);
	}

    private static void processDelete() {
        ListDao.delete("/list", 48);
    }

}
