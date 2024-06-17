package oopproject1.frontEnd.commands;

import java.util.List;

import oopproject1.admin.KafkaCluster;
import oopproject1.admin.KafkaProducer;
import oopproject1.admin.KafkaTopic;
import oopproject1.data.RecordsFile;
import oopproject1.utilities.Globals;

public class CommandInsert extends CliCommand {
    protected CommandInsert() {
        super("insert",
                "Insert a new file to a topic.\n\tUsage: insert <FILE_NAME> <TOPIC_NAME> <KEY_COLUMN_NAME>.\n\tKey column name should be specified only if the chosen topic is a keyed topic, otherwise it will be ignored.");
    }

    @Override
    public void callback(KafkaCluster cluster, List<String> params) {
        if (params.size() < 2) {
            System.out.println(getName() + ": " + Globals.errInvalidArguments);
            return;
        }

        String fileName = params.get(0);
        String topicName = params.get(1);

        RecordsFile dat = RecordsFile.readFile(fileName);
        if (dat == null) {
            System.out.println(Globals.errFileNotFound);
            return;
        }

        KafkaTopic topic = cluster.findTopicByName(topicName);
        if (topic == null) {
            System.out.println(Globals.errTopicNotFound);
            return;
        }
        
        String keyColumn = "";
        if (topic.isKeyed()) {
            if (params.size() < 3) {
                System.out.println(getName() + ": " + Globals.errInvalidArguments + ": " + "you must specify the name of the key column for keyed topics");
                return;
            }

            keyColumn = params.get(2);
        }

        KafkaProducer[] producers = topic.getProducers();
        int messagePerProducer = dat.getDataTable().size() / producers.length;
        int remainder = dat.getDataTable().size() % topic.getProducers().length;

        int offset = 0;
        for (KafkaProducer producer : producers) {
            for (int i = 0; i < messagePerProducer; i++) {
                producer.sendMessage(keyColumn, dat.toString(offset + i));
            }
            offset += messagePerProducer;

            if (remainder >= 1) {
                producer.sendMessage(keyColumn, dat.toString(++offset));
                remainder--;
            }
        }
    }
}
