package oopproject1.commands;

import java.util.List;

import oopproject1.admin.KafkaCluster;
import oopproject1.admin.KafkaProducer;
import oopproject1.admin.KafkaTopic;
import oopproject1.data.FileData;

public class CommandInsert extends CliCommand {
    public CommandInsert() {
        super("insert",
                "Insert a new file to a topic.\n         Usage: insert <FILE_NAME> <TOPIC_NAME> <KEY_COLUMN_NAME>. Key column name should be specified only if the chosen topic is a keyed topic, otherwise it will be ignored.");
    }

    @Override
    public void callback(KafkaCluster cluster, List<String> params) {
        if (params.size() < 2) {
            System.out.println(getName() + ": invalid arguments");
            return;
        }

        String fileName = params.get(0);
        String topicName = params.get(1);
        String keyColumn = "";
        
        KafkaTopic topic = cluster.findTopicByName(topicName);
        KafkaProducer[] producers = topic.getProducers();
        FileData dat = FileData.readFile(fileName);

        int messagePerProducer = dat.getDataTable().size()/producers.length;
        // int remainder = dat.getDataTable().size() % topic.getProducers().length;

        if (topic != null) {
            if(topic.isKeyed()) {
                if (params.size() < 3) {
                    System.out.println(getName() + ": you must specify the name of the key column for keyed topics");
                    return;
                }
                
                keyColumn = params.get(2);
                int offset = 0;
                for (KafkaProducer producer : producers) {
                    for (int i = 0; i < messagePerProducer; i++) {
                        producer.sendMessage(keyColumn, dat.toString(offset + i));
                    }
                    offset += messagePerProducer-1;
                }
            }
        }    

        System.out.println("Topic not found");
    }
}
