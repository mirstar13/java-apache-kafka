package oopproject1.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import oopproject1.admin.KafkaCluster;

public abstract class CliCommand {
    private String name;
    private String description;
    
    public CliCommand(String name, String description) {
        this.name = name;
        this.description = description;
    }
    
    // GETTERS

    public String getName() {
        return name;
    }

    private String getDescription() {
        return description;
    }

    // METHODS

    public String toString(){
        return name + " : " + description;
    }

    public abstract void callback(KafkaCluster cluster, List<String> params);
    
    public static HashMap<String, CliCommand> getCommands(){
        HashMap<String, CliCommand> m = new HashMap<String, CliCommand>();

        m.put("exit", new CommandExit());
        m.put("help", new CommandHelp());
        m.put("insert", new CommandInsert());
        m.put("test", new CommandTest());
        m.put("complete", new CommandComplete());

        return m;
    }
}

