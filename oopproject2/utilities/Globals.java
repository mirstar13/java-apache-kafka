package oopproject2.utilities;

import java.io.File;

public class Globals {
    public static final String baseDir = System.getProperty("user.dir");
    public static final String pathToData = "oopproject2" + File.separator + "data";
    public static final String pathToConsumerMessages = "consumer_messages";

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

    // Command Description
    public static final String commandGUIDesciption = "Opens the user interface.\n\tUsage: gui <new_cluster> <max_brokers> <max_topics_per_broker>. Arguments are optional.\n\tIf you use new_cluster a new KafkaCLuster will be initialized for the gui.\n\tIf <max_brokers> or <max_topics_per_broker>, are set to 0, then their default values will be used.\n\tIf <max_topics_per_broker> or both <max_brokers> and <max_topics_per_broker> are left empty, their default values will be used";
    public static final String commandInsertDescription = "Insert a new file to a topic.\n\tUsage: insert <FILE_NAME> <TOPIC_NAME> <KEY_COLUMN_NAME>.\n\tKey column name should be specified only if the chosen topic is a keyed topic, otherwise it will be ignored.";
    public static final String commandShowDescription = "Prints available topics.\n\tUsage: show <include_details>.\n\tUse with include details to print an extensive description of each topic";
    public static final String commandCompleteDescription = "Completes the data insertion preccess and initiates the message reading proccess.";
    public static final String commadnTestDescription = "Tests the message writting and reading proccess";
    public static final String commandHelpDescription = "Displays this help message";
    public static final String commandExitDescription = "Exits the app";
}
