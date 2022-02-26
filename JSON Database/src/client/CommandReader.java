package client;

import com.beust.jcommander.Parameter;

public class CommandReader {
    @Parameter(names = { "-t"})
    private String requestType;

    @Parameter(names = "-k")
    private String index;

    @Parameter(names = "-v")
    private String textToSave;

    @Parameter(names = "-in")
    private String requestFromFile;

    public String getRequestType() {
        return requestType;
    }

    public String getIndex() {
        return index;
    }

    public String getTextToSave() {
        return textToSave;
    }

    public String getRequestFromFile() {
        return requestFromFile;
    }
}
