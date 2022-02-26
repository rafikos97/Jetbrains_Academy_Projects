package server;

import java.util.concurrent.Callable;

public class ClientHandler implements Callable<String> {
    private static final Database db = Database.getInstance();
    private static final DatabaseHandler dbh = new DatabaseHandler(db);
    private final String arguments;

    public ClientHandler(String arguments) {
        this.arguments = arguments;
    }

    @Override
    public String call() {
        return dbh.startInteraction(arguments);
    }
}
