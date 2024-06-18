/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package oopproject2.frontEnd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import oopproject2.admin.KafkaBroker;
import oopproject2.admin.KafkaCluster;
import oopproject2.admin.KafkaConsumer;
import oopproject2.admin.KafkaProducer;
import oopproject2.admin.KafkaTopic;
import oopproject2.frontEnd.commands.CliCommand;
import oopproject2.utilities.Globals;

/**
 *
 * @author ngiatrakos
 */
public class KafkaCLI {
    public static void main(String[] args) {
        KafkaCluster kafkaCluster = init();
        Scanner scanner = new Scanner(System.in);

        System.out.print(Globals.cliPrompt);

        while (true) {
            String[] spltAdminInput = scanner.nextLine().trim().split(" ");
            List<String> params = new ArrayList<>();
            String command = spltAdminInput[0];

            if (command.equals("")) {
                System.out.print(Globals.cliPrompt);
                
                continue;
            }

            for (int i = 1; i < spltAdminInput.length; i++) {
                params.add(spltAdminInput[i]);
            }

            HashMap<String, CliCommand> commands = CliCommand.getCommands();

            if (commands.containsKey(command)) {
                CliCommand cmd = commands.get(command);

                if (cmd.getName().equals("exit")) {
                    scanner.close();
                }

                if (cmd.getName().equals("gui")) {
                    cmd.callback(kafkaCluster, params);
                } else {
                    cmd.callback(kafkaCluster, params);
                    System.out.print(Globals.cliPrompt);
                }
            } else {
                System.out.println(command + ": Unknown command");
                System.out.print(Globals.cliPrompt);
            }

        }
    }

    public static KafkaCluster init() {
        int maxBrokersInCluster = 10;
        int maxTopicsPerBroker = 10;
        KafkaCluster cluster = new KafkaCluster(maxBrokersInCluster, maxTopicsPerBroker);
        try {
            for (int i = 0; i < maxBrokersInCluster; i++) {
                cluster.insertBroker(new KafkaBroker("127.0.0.1", 9090 + i, 10));
            }

            KafkaTopic[] topics = new KafkaTopic[6];
            cluster.addTopic("stock_prices", 30, 50, 20, 4, true);
            topics[0] = cluster.findTopicByName("stock_prices");

            cluster.addTopic("ship_locations", 20, 7, 12, 3, true);

            topics[1] = cluster.findTopicByName("ship_locations");

            cluster.addTopic("power_consumptions", 30, 33, 33, 4, false);
            topics[2] = cluster.findTopicByName("power_consumptions");

            cluster.addTopic("weather_conditions", 4, 3, 7, 2, true);
            topics[3] = cluster.findTopicByName("weather_conditions");

            cluster.addTopic("plane_locations", 6, 12, 12, 2, true);
            topics[4] = cluster.findTopicByName("plane_locations");

            cluster.addTopic("bookings", 4, 2, 2, 1, false);
            topics[5] = cluster.findTopicByName("bookings");

            for (int i = 0; i < topics.length; i++) {
                if (topics[i] != null) {
                    for (int j = 0; j < topics[i].getProducers().length; j++) {
                        topics[i].addProducer(new KafkaProducer(topics[i]));
                    }

                    for (int j = 0; j < topics[i].getConsumers().length; j++) {
                        topics[i].addConsumer(new KafkaConsumer(topics[i]));
                    }
                }

                continue;
            }
        } catch (Exception e) {
            System.out.println("Cluster initialization failed. Error: " + e.getMessage());
            // e.printStackTrace();
        }

        return cluster;
    }
}
