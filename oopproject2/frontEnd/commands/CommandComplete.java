package oopproject2.frontEnd.commands;

import java.util.List;

import oopproject2.admin.KafkaBroker;
import oopproject2.admin.KafkaCluster;
import oopproject2.admin.KafkaConsumer;
import oopproject2.admin.KafkaTopic;

public class CommandComplete extends CliCommand {
    protected CommandComplete() {
        super("complete", "Completes the data insertion preccess and initiates the message reading proccess.");
    }

    @Override
    public void callback(KafkaCluster kafkaCluster, List<String> params) {
        System.out.println("Starting to read messages");

        List<KafkaBroker> brokers = kafkaCluster.getBrokers();
        for (KafkaBroker broker : brokers) {
            List<KafkaTopic> topics = broker.getTopics();
            for (KafkaTopic topic : topics) {
                KafkaConsumer[] currentConsumers = topic.getConsumers();
                for (int i = 0; i < currentConsumers.length; i++) {
                    currentConsumers[i].receiveMessage(i);
                }
            }
        }

        System.out.println("Finished reading messages");

        int consumerCount = 0;
        for (KafkaBroker broker : brokers) {
            List<KafkaTopic> topics = broker.getTopics();
            for (KafkaTopic topic : topics) {
                KafkaConsumer[] currentConsumers = topic.getConsumers();
                for (int i = 0; i < currentConsumers.length; i++) {
                    currentConsumers[i].writeMessageStackToFile("consumer_" + consumerCount++);;
                }
            }
        }
    }
}
