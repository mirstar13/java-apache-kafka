package oopproject2.frontEnd.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import oopproject2.admin.KafkaCluster;
import oopproject2.utilities.Globals;

public class CommandTest extends CliCommand {
    protected CommandTest() {
        super("test", Globals.commadnTestDescription);
    }

    public void callback(KafkaCluster cluster, List<String> params) {
        HashMap<String, CliCommand> commands = getCommands();     

        CliCommand insert = commands.get("insert");
        CliCommand complete = commands.get("complete");
        List<String> args = new ArrayList<>();
        
        args.add("unsortedHousePowerConsumTruncated.txt");
        args.add("power_consumptions");
        insert.callback(cluster, args);

        args.clear();

        args.add("unsortedAISTruncated.txt");
        args.add("ship_locations");
        args.add("shipid");
        insert.callback(cluster, args);

        args.clear();

        args.add("unsortedFinancialDatasetTruncated.txt");
        args.add("stock_prices");
        args.add("key");
        insert.callback(cluster, args);

        args.clear();

        complete.callback(cluster, params);
    }
}
