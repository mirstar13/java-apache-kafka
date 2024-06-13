package oopproject1.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import oopproject1.admin.KafkaCluster;
import oopproject1.admin.KafkaProducer;
import oopproject1.admin.KafkaTopic;
import oopproject1.data.FileData;

public class CommandTest extends CliCommand {
    protected CommandTest() {
        super("test", "debug test");
    }

    public void callback(KafkaCluster cluster, List<String> params) {
        FileData dat = FileData.readFile("unsortedAISTruncated.txt");

        System.out.println(dat.getHeaders().get(8));
    }
}
