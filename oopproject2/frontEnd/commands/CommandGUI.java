package oopproject2.frontEnd.commands;

import java.util.List;

import oopproject2.admin.KafkaCluster;
import oopproject2.controller.KafkaClusterController;
import oopproject2.utilities.Globals;

public class CommandGUI extends CliCommand {
    protected CommandGUI() {
        super("gui", Globals.commandGUIDesciption);
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
