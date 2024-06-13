package oopproject1.commands;

import java.util.List;

import oopproject1.admin.KafkaCluster;
import oopproject1.data.RecordsFile;

public class CommandTest extends CliCommand {
    protected CommandTest() {
        super("test", "debug test");
    }

    public void callback(KafkaCluster cluster, List<String> params) {
        RecordsFile dat = RecordsFile.readFile("unsortedAISTruncated.txt");

        System.out.println(dat.getHeaders().get(8));
    }
}
