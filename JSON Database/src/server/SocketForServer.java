package server;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class SocketForServer {
    private static final int PORT = 23456;
    private static final String ADDRESS = "127.0.0.1";


    public static void connectServer() {
        try (ServerSocket server = new ServerSocket(PORT, 50, InetAddress.getByName(ADDRESS))) {
            System.out.println("Server started!");

            while(true) {
                try (
                        Socket socket = server.accept();
                        DataInputStream input = new DataInputStream(socket.getInputStream());
                        DataOutputStream output = new DataOutputStream(socket.getOutputStream())
                ) {
                    String received = input.readUTF();

                    ExecutorService executor = Executors.newCachedThreadPool();
                    ClientHandler clientHandler = new ClientHandler(received);

                    Future<String> f = executor.submit(clientHandler);

                    String outputString = f.get(10, TimeUnit.MILLISECONDS);

                    output.writeUTF(outputString);

                    //CLOSE THE SERVER
                    Gson gson = new Gson();
                    JsonObject jsonObject = gson.fromJson(received, JsonObject.class);
                    JsonElement jsonElement = jsonObject.get("type");
                    String firstWord = jsonElement.getAsString();

                    if (firstWord.equals("exit")) {
                        server.close();
                        System.out.println("Server closed!");
                        break;
                    }

                } catch (ExecutionException | InterruptedException | TimeoutException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
