package server;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import server.exceptions.BadRequestException;

import java.util.HashMap;
import java.util.Map;

public class DatabaseHandler {
    private final Database db;

    public DatabaseHandler(Database db) {
        this.db = db;
    }

    public String startInteraction(String arguments) {
        Gson gson = new Gson();

        JsonObject jsonObject = gson.fromJson(arguments, JsonObject.class);
        JsonElement jsonElement = jsonObject.get("type");
        String firstWord = jsonElement.getAsString();

        switch (firstWord) {
            case "get":
                return get(arguments);
            case "set":
                return set(arguments);
            case "delete":
                return delete(arguments);
            case "exit":
                return exit();
            default:
                throw new BadRequestException();
        }
    }


    private String get(String input) {
        JsonObject result = new JsonObject();
        Gson gson = new Gson();

        JsonObject jsonObject = gson.fromJson(input, JsonObject.class);
        JsonElement key = jsonObject.get("key");

        JsonElement resultJsonElement = db.getCell(key);

        if (resultJsonElement == null) {
            result.addProperty("response", "ERROR");
            result.addProperty("reason", "No such key");
        } else {
            result.addProperty("response", "OK");
            result.add("value", resultJsonElement);
        }

        return gson.toJson(result);
    }

    private String set(String input) {
        Map<String, String> resultJson = new HashMap<>();
        Gson gson = new Gson();

        JsonObject jsonObject = gson.fromJson(input, JsonObject.class);
        JsonElement key = jsonObject.get("key");
        JsonElement value = jsonObject.get("value");

        boolean methodResult = db.setCell(key, value);

        if (methodResult) {
            resultJson.put("response", "OK");
        }

        return gson.toJson(resultJson);
    }

    private String delete(String input) {
        Map<String, String> resultJson = new HashMap<>();
        Gson gson = new Gson();

        JsonObject jsonObject = gson.fromJson(input, JsonObject.class);
        JsonElement key = jsonObject.get("key");

        boolean methodResult = db.deleteCell(key);

        if (methodResult) {
            resultJson.put("response", "OK");
        } else {
            resultJson.put("response", "ERROR");
            resultJson.put("reason", "No such key");
        }

        return gson.toJson(resultJson);
    }

    private String exit() {
        Map<String, String> resultJson = new HashMap<>();
        Gson gson = new Gson();

        resultJson.put("response", "OK");
        return gson.toJson(resultJson);
    }
}
