package client;

import com.beust.jcommander.JCommander;
import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class ClientConnectionHandler {

    public static void init(String[] args) {
        CommandReader cr = new CommandReader();
        Map<String, String> argMap = new HashMap<>();
        String output;

        JCommander jc = JCommander.newBuilder()
                .addObject(cr)
                .build();
        jc.parse(args);

        if (cr.getRequestFromFile() != null) {
            output = fileRead(cr.getRequestFromFile());
        } else {
            argMap.put("type", cr.getRequestType());
            if (cr.getIndex() != null) {
                argMap.put("key", cr.getIndex());
            }
            if (cr.getTextToSave() != null) {
                argMap.put("value", cr.getTextToSave());
            }

            Gson gson = new Gson();
            output = gson.toJson(argMap);
        }

        ClientSocket.connect(output);
    }

    private static String fileRead(String fileName) {
        String path = System.getProperty("user.dir") + "\\src\\client\\data\\" + fileName;
//        String path = "C:\\Users\\Rafiki\\IdeaProjects\\JSON Database\\JSON Database\\task\\src\\server\\data\\db.json";

        String content = null;

        try {
            content = Files.readString(Path.of(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }
}
