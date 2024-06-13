package oopproject1.commands;

import java.util.HashMap;
import java.util.List;

import oopproject1.admin.KafkaCluster;

public class CommandHelp extends CliCommand {
    public CommandHelp() {
        super("help", "Displays this help message");
    }

    @Override
    public void callback(KafkaCluster cluster, List<String> params){
        HashMap<String, CliCommand> commands = CliCommand.getCommands();

        System.out.println();
        for (CliCommand command : commands.values()) {
            System.out.println(command.toString());
        }
        System.out.println();

    }
}
