package oopproject1.utilities;

import java.io.File;

public class Globals {
    public static final String baseDir = System.getProperty("user.dir") + File.separator;
    public static final String pathToData = "oopproject1" + File.separator + "data";

    public static final String cliPrompt = "Apache Kafka CLI > ";

    public static final String messageSeperator = " : ";

    // Max Values
    public static final int maxBrokers = 50;
    public static final int maxTopics = 100;
    public static final int maxNumPartitions = 20;
    public static final int maxConsumers = 100;
    public static final int maxProducers = 100;
    public static final int maxReplicationFactor = 20;

    // Response messages 
    public static final String respAlreadyExists = "A similar record seems to already exist"; 
    public static final String respUpdatedSuccessfully = "Recored updated successfully";
    public static final String respDeletedSuccessfully = "Record deleted successfully";
    public static final String respAddedSuccessfully = "Record added successfully";

    // Error messages
    public static final String errGUI = "GUI: ";
    public static final String errCLI = "CLI: ";
    public static final String errTopicNotFound = "Topic not found";
    public static final String errFileNotFound = "File not found";
    public static final String errInvalidArguments = "Invalid arguments";
    public static final String errBrokerNotFound = "Broker not found";
    public static final String errSomethingWentWrong = "Something went wrong";
    public static final String errNothingIsSelected = "Nothing is selected";
    public static final String errRecordNotDeleted = "Record was not deleted";
    public static final String errRecordNotAdded = "Record was not added";
    public static final String errRecordNotUpdated = "Record was not updated";
    public static final String errMaxBrokersReached = "Reached the max Brokers limit";
    public static final String errMaxProducersReached = "Reached the max Producers limit";
    public static final String errMaxConsumersReached = "Reached the max Consumers limit";
    public static final String errMaxTopicsReached = "Reached the max Topics limit";
    public static final String errExceedsMaxRange = "Exceeds max range";
}
