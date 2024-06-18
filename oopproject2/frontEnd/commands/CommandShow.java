package oopproject2.frontEnd.commands;

import java.util.List;

import oopproject2.admin.KafkaCluster;

public class CommandShow extends CliCommand {
    protected CommandShow() {
        super("show",
                "Prints available topics.\n\tUsage: show <include_details>.\n\tUse with include details to print an extensive description of each topic");
    }

    public void callback(KafkaCluster cluster, List<String> params) {
        if (params.size() > 0 && params.get(0).equals("include_details")) {
            cluster.listAllTopicsAcrossBrokers(true);
        } else {
            cluster.listAllTopicsAcrossBrokers();
        }
    }
}
