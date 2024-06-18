package oopproject2.frontEnd.commands;

import java.util.HashMap;
import java.util.List;

import oopproject2.admin.KafkaCluster;
import oopproject2.utilities.Globals;

public class CommandHelp extends CliCommand {
    protected CommandHelp() {
        super("help", Globals.commandHelpDescription);
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
