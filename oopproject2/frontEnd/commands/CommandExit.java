package oopproject2.frontEnd.commands;

import java.util.List;

import oopproject2.admin.KafkaCluster;
import oopproject2.utilities.Globals;

public class CommandExit extends CliCommand {
    protected CommandExit() {
        super("exit", Globals.commandExitDescription);
    }

    @Override
    public void callback(KafkaCluster cluster, List<String> params) {
        System.exit(0);
    }
}
