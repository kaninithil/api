/**
 * 
 */
package com.api.automation.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @author ssama1
 */
public class JsonUtil {

    public static String parseJson(String jsonMessage, String key) {

        JsonParser parser = new JsonParser();
        System.out.println("json message" + jsonMessage);
        JsonObject o = parser.parse(jsonMessage).getAsJsonObject();
        JsonElement jsonElement = o.get(key);
        System.out.println("jsonElement:" + jsonElement);
        if (jsonElement != null) {
            if (!jsonElement.isJsonNull()) {
                String value = jsonElement.toString();
                System.out.println("value:" + value);
                return value;
            }
        }

        return null;

    }

    public static String parseJsonArray(String jsonMessage, String key) {

        JsonParser parser = new JsonParser();
        System.out.println("json message" + jsonMessage);
        JsonArray jsonArray = parser.parse(jsonMessage).getAsJsonArray();
        JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();
        JsonElement element = jsonObject.get(key);
        System.out.println("jsonElement:" + element);
        if (element != null) {
            if (!element.isJsonNull()) {
                String value = element.toString();
                System.out.println("value:" + value);
                return value;
            }
        }

        return null;

    }

    public static String parseJsonArrayValue(String jsonMessage) {

        JsonParser parser = new JsonParser();
        System.out.println("json message" + jsonMessage);
        JsonArray jsonArray = parser.parse(jsonMessage).getAsJsonArray();
        return jsonArray.get(0).getAsString();

    }
    /*
     * public static String updateJson(String jsonMessage, List<String> keys) { JsonParser parser =
     * new JsonParser(); JsonObject obj = parser.parse(jsonMessage).getAsJsonObject(); for (String
     * key : keys) { JsonElement jsonElement = obj.get(key); System.out.println("jsonElement:" +
     * jsonElement); if (!jsonElement.isJsonNull()) { obj.addProperty(key,
     * RandomStringUtils.random(5)); } } return obj.toString(); }
     */

    public static void main(String[] args) throws Exception {
        String json = "[{\"key\":\"PostCode\",\"messageIds\":[\"MSG_01\"]}]";
        JsonUtil.parseJsonArrayValue(json);
    }
}
