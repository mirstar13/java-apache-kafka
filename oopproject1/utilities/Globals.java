package oopproject1.utilities;

import java.io.File;

public class Globals {
    public static final String baseDir = System.getProperty("user.dir") + File.separator;
    public static final String pathToData = "oopproject1" + File.separator + "data";

    public static final String cliPrompt = "Apache Kafka CLI > ";

    // Error messages
    public static final String errGUI = "GUI: ";
    public static final String errCLI = "CLI: ";
    public static final String errTopicNotFound = "Topic not found";
    public static final String errFileNotFound = "File not found";
    public static final String errInvalidArguments = "Invalid arguments";
    public static final String errBrokerNotFound = "Broker not found";
}
