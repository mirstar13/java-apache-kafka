package oopproject1.frontEnd.commands;

import java.util.List;

import oopproject1.admin.KafkaCluster;
import oopproject1.controller.KafkaClusterController;

public class CommandGUI extends CliCommand {
    protected CommandGUI() {
        super("gui",
                "Opens the user interface.\n\tUsage: gui <new_cluster> <max_brokers> <max_topics_per_broker>. Arguments are optional.\n\tIf you use new_cluster a new KafkaCLuster will be initialized for the gui.\n\tIf <max_brokers> or <max_topics_per_broker>, are set to 0, then their default values will be used.\n\tIf <max_topics_per_broker> or both <max_brokers> and <max_topics_per_broker> are left empty, their default values will be used");
    }

    @Override
    public void callback(KafkaCluster cluster, List<String> params) {
        if (params.size() > 0) {
            if (params.get(0).equals("new_cluster")) {
                String strMaxBrokers;
                int maxBrokers = KafkaCluster.DEFAULT_MAX_BROKERS;
                int maxTopicsPerBroker = KafkaCluster.DEFAULT_MAX_TOPICS_PER_BROKER;
                KafkaCluster newCluster;
                KafkaClusterController controller;

                switch (params.size()) {
                    case 1:
                        newCluster = new KafkaCluster(maxBrokers, maxTopicsPerBroker);
                        controller = new KafkaClusterController(newCluster);
                        controller.start();
                        return;
                    case 2:
                        strMaxBrokers = params.get(1);

                        try {
                            maxBrokers = Integer.parseInt(strMaxBrokers);
                        } catch (NumberFormatException ex) {
                            System.out.println("gui: invalid arguments");
                            return;
                        }

                        newCluster = new KafkaCluster(maxBrokers, maxTopicsPerBroker);
                        controller = new KafkaClusterController(newCluster);
                        controller.start();
                        return;
                    default:
                        strMaxBrokers = params.get(1);
                        String strMaxTopicsPerBroker = params.get(2);

                        try {
                            maxBrokers = Integer.parseInt(strMaxBrokers);
                            maxTopicsPerBroker = Integer.parseInt(strMaxTopicsPerBroker);
                        } catch (NumberFormatException ex) {
                            System.out.println("gui: invalid arguments");
                            return;
                        }

                        if (maxBrokers == 0) {
                            maxBrokers = KafkaCluster.DEFAULT_MAX_BROKERS;
                        }

                        if (maxTopicsPerBroker == 0) {
                            maxTopicsPerBroker = KafkaCluster.DEFAULT_MAX_TOPICS_PER_BROKER;
                        }

                        newCluster = new KafkaCluster(maxBrokers, maxTopicsPerBroker);
                        controller = new KafkaClusterController(newCluster);
                        controller.start();
                        return;
                }
            }
        }

        KafkaClusterController controller = new KafkaClusterController(cluster);
        controller.start();
    }
}
