package server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DbUtils {
    public static void writeDbToFile(JsonObject db, File file) {
        try (FileWriter fileWriter = new FileWriter(file)) {
            new Gson().toJson(db, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JsonObject readFromFile(File file) {
        if (file.exists()) {
            try (JsonReader reader = new JsonReader(new FileReader(file))) {
                JsonObject jsonObject = new Gson().fromJson(reader, JsonObject.class);

                if (jsonObject.size() != 0) {
                    return jsonObject;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new JsonObject();
    }
}

