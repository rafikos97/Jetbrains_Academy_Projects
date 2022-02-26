package server;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import server.exceptions.NoSuchKeyException;

import java.io.File;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Database {
    private final JsonObject db;

//    String pathString = "C:\\Users\\Rafiki\\IdeaProjects\\JSON Database\\JSON Database\\task\\src\\server\\data\\db.json";
    String pathString = System.getProperty("user.dir") + "\\src\\server\\data\\db.json";
    File file = new File(pathString);

    ReadWriteLock lock = new ReentrantReadWriteLock();
    Lock readLock = lock.readLock();
    Lock writeLock = lock.writeLock();

    {
        this.db = DbUtils.readFromFile(file);
    }

    private static final Database instance = new Database();

    private Database() { }

    public static Database getInstance() {
        return instance;
    }

    public JsonElement getCell(JsonElement key){
        try {
            readLock.lock();
            if (key.isJsonPrimitive() && db.has(key.getAsString())){
                return db.get(key.getAsString());
            }
            else if (key.isJsonArray()) {
                return findElement(key.getAsJsonArray(), false);
            }
            return null;

        } finally {
            readLock.unlock();
        }
    }

    public boolean setCell(JsonElement key, JsonElement value) {
        try {
            writeLock.lock();
            if (key.isJsonPrimitive()) {
                db.add(key.getAsString(), value);
                DbUtils.writeDbToFile(db, file);
                return true;
            } else if (key.isJsonArray()) {
                JsonArray keys = key.getAsJsonArray();
                String toAdd = keys.remove(keys.size() - 1).getAsString();
                findElement(keys, true).getAsJsonObject().add(toAdd, value);
                DbUtils.writeDbToFile(db, file);
                return true;
            } else {
                return false;
            }
        } finally {
            writeLock.unlock();
        }
    }

    public boolean deleteCell(JsonElement key) {
        try {
            writeLock.lock();
            if (key.isJsonPrimitive() && db.has(key.getAsString())) {
                db.remove(key.getAsString());
                DbUtils.writeDbToFile(db, file);
                return true;
            } else if (key.isJsonArray()) {
                JsonArray keys = key.getAsJsonArray();
                String toRemove = keys.remove(keys.size() - 1).getAsString();
                findElement(keys, false).getAsJsonObject().remove(toRemove);
                DbUtils.writeDbToFile(db, file);
                return true;
            } else {
                return false;
            }
        } finally {
            writeLock.unlock();
        }
    }

    private JsonElement findElement(JsonArray keys, boolean createIfAbsent) {
        JsonElement tmp = db;
        if (createIfAbsent) {
            for (JsonElement key: keys) {
                if (!tmp.getAsJsonObject().has(key.getAsString())) {
                    tmp.getAsJsonObject().add(key.getAsString(), new JsonObject());
                }
                tmp = tmp.getAsJsonObject().get(key.getAsString());
            }
        } else {
            for (JsonElement key: keys) {
                if (!key.isJsonPrimitive() || !tmp.getAsJsonObject().has(key.getAsString())) {
                    throw new NoSuchKeyException();
                }
                tmp = tmp.getAsJsonObject().get(key.getAsString());
            }
        }
        return tmp;
    }
}
