package oopproject2.frontEnd.commands;

import java.util.List;

import oopproject2.admin.KafkaCluster;
import oopproject2.utilities.Globals;

public class CommandShow extends CliCommand {
    protected CommandShow() {
        super("show", Globals.commandShowDescription);
    }

    public void callback(KafkaCluster cluster, List<String> params) {
        if (params.size() > 0 && params.get(0).equals("include_details")) {
            cluster.listAllTopicsAcrossBrokers(true);
        } else {
            cluster.listAllTopicsAcrossBrokers();
        }
    }
}
