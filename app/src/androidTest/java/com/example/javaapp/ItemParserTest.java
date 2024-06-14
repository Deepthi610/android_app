package com.example.javaapp;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ItemParserTest {

    @Test
    public void parseJson_correctlyParsesItems() {
        String json = "[{\"id\": 1, \"listId\": 1, \"name\": \"Item 1\"}," +
                "{\"id\": 2, \"listId\": 1, \"name\": \"Item 2\"}," +
                "{\"id\": 3, \"listId\": 2, \"name\": \"Item 3\"}," +
                "{\"id\": 4, \"listId\": 2, \"name\": \"\"}," +
                "{\"id\": 5, \"listId\": 3, \"name\": null}]";

        List<Item> items = parseJson(json);

        assertEquals(3, items.size());
        assertEquals("Item 1", items.get(0).getName());
        assertEquals("Item 2", items.get(1).getName());
        assertEquals("Item 3", items.get(2).getName());
    }

    private List<Item> parseJson(String json) {
        List<Item> itemList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                int listId = jsonObject.getInt("listId");
                String name = jsonObject.optString("name");
                if (name != null && !name.isEmpty() && !name.equals("null")) {
                    itemList.add(new Item(id, listId, name));
                }
            }
            Collections.sort(itemList, new Comparator<Item>() {
                @Override
                public int compare(Item o1, Item o2) {
                    int listIdCompare = Integer.compare(o1.getListId(), o2.getListId());
                    if (listIdCompare == 0) {
                        return o1.getName().compareTo(o2.getName());
                    }
                    return listIdCompare;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemList;
    }
}

