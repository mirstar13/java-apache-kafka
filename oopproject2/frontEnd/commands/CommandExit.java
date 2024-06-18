package oopproject2.frontEnd.commands;

import java.util.List;

import oopproject2.admin.KafkaCluster;

public class CommandExit extends CliCommand {
    protected CommandExit() {
        super("exit", "Exits the app");
    }

    @Override
    public void callback(KafkaCluster cluster, List<String> params) {
        System.exit(0);
    }
}
