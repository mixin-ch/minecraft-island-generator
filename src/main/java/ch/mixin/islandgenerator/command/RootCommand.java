package ch.mixin.islandgenerator.command;

import ch.mixin.islandgenerator.command.commandList.*;
import ch.mixin.islandgenerator.main.IslandGeneratorPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class RootCommand extends SubCommand implements CommandExecutor {
    private final HashMap<String, SubCommand> subCommandMap;
    private final String commandName;

    public RootCommand(IslandGeneratorPlugin plugin, String commandName) {
        super(plugin);
        this.commandName = commandName;
        subCommandMap = new HashMap<>();
        subCommandMap.put("reload", new ReloadCommand(plugin));
        subCommandMap.put("generate", new GenerateCommand(plugin));
        subCommandMap.put("regenerate", new RegenerateCommand(plugin));
        subCommandMap.put("lookupLoot", new LookupLootCommand(plugin));
        subCommandMap.put("config", new ConfigCommand(plugin));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase(commandName))
            return true;

        List<String> arguments = new ArrayList<>(Arrays.asList(args));
        fetchCommand(arguments).execute(sender, arguments);
        return true;
    }

    @Override
    public SubCommand fetchCommand(List<String> arguments) {
        if (arguments.size() == 0)
            return this;

        SubCommand subCommand = subCommandMap.get(arguments.get(0));

        if (subCommand == null)
            return this;

        arguments.remove(0);
        return subCommand.fetchCommand(arguments);
    }

    @Override
    public void execute(CommandSender sender, List<String> arguments) {
        sender.sendMessage(ChatColor.RED + "IslandGenerator Command not found.");
    }

    @Override
    public List<String> getOptions(List<String> arguments) {
        return new ArrayList<>(subCommandMap.keySet());
    }
}
