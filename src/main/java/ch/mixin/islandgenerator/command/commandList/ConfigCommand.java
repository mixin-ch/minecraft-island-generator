package ch.mixin.islandgenerator.command.commandList;

import ch.mixin.islandgenerator.command.SubCommand;
import ch.mixin.islandgenerator.command.commandList.config.*;
import ch.mixin.islandgenerator.main.IslandGeneratorPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConfigCommand extends SubCommand {
    private final HashMap<String, SubCommand> subCommandMap;
    private final HashMap<String, Integer> numberMinList;
    private final List<String> integerList;

    public ConfigCommand(IslandGeneratorPlugin plugin) {
        super(plugin);
        subCommandMap = new HashMap<>();
        subCommandMap.put("worlds", new WorldsCommand(plugin));
        subCommandMap.put("islandTypeWeights", new IslandTypeWeightsCommand(plugin));

        numberMinList = new HashMap<>();
        numberMinList.put("tickBuffer", 0);
        numberMinList.put("lootMultiplier", 0);
        numberMinList.put("spawnRadius", 1);
        numberMinList.put("islandDistance", 1);
        numberMinList.put("islandRadius", 1);

        integerList = new ArrayList<>();
        integerList.add("maximumHeight");
        integerList.add("minimumHeight");
        integerList.add("glassSphereMaximumHeight");
        integerList.add("glassSphereMinimumHeight");
        integerList.add("glassSphereExtraRadiusMax");
        integerList.add("glassSphereExtraRadiusMin");
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
        if (arguments.size() != 2) {
            sender.sendMessage(ChatColor.RED + "Invalid Argument Number.");
            return;
        }

        if (!sender.hasPermission("islandGenerator.config")) {
            sender.sendMessage(ChatColor.RED + "You lack Permission.");
            return;
        }

        String key = arguments.get(0);
        if (numberMinList.containsKey(key) || integerList.contains(key)) {
            boolean checkMinimum = false;
            int min = 0;

            if (numberMinList.containsKey(key)) {
                checkMinimum = true;
                min = numberMinList.get(key);
            }

            int value;

            try {
                value = Integer.parseInt(arguments.get(1));
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.RED + "Invalid Number Format.");
                return;
            }

            if (checkMinimum && value < min) {
                sender.sendMessage(ChatColor.RED + "Value must be at least " + min + ".");
                return;
            }

            plugin.getConfig().set(key, value);
            plugin.saveConfig();
            sender.sendMessage(ChatColor.GREEN + "Success");
            return;
        }

        sender.sendMessage(ChatColor.RED + "Invalid Key.");
    }

    @Override
    public List<String> getOptions(List<String> arguments) {
        List<String> options = new ArrayList<>();

        if (arguments.size() == 1) {
            options.addAll(subCommandMap.keySet());
            options.addAll(numberMinList.keySet());
            options.addAll(integerList);
        } else if (arguments.size() == 2) {
            options.add("<value>");
        }

        return options;
    }
}
