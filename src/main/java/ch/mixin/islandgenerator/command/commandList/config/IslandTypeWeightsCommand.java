package ch.mixin.islandgenerator.command.commandList.config;

import ch.mixin.islandgenerator.command.SubCommand;
import ch.mixin.islandgenerator.islandGeneration.IslandType;
import ch.mixin.islandgenerator.main.IslandGeneratorPlugin;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IslandTypeWeightsCommand extends SubCommand {
    public IslandTypeWeightsCommand(IslandGeneratorPlugin plugin) {
        super(plugin);
    }

    @Override
    public SubCommand fetchCommand(List<String> arguments) {
        return this;
    }

    @Override
    public void execute(CommandSender sender, List<String> arguments) {
        if (arguments.size() == 2) {
            sender.sendMessage(ChatColor.RED + "Invalid Argument Number.");
            return;
        }

        if (!sender.hasPermission("islandGenerator.config")) {
            sender.sendMessage(ChatColor.RED + "You lack Permission.");
            return;
        }

        String type = arguments.get(0);

        try {
            IslandType.valueOf(type);
        } catch (IllegalArgumentException e) {
            sender.sendMessage(ChatColor.RED + "IslandType not found.");
            return;
        }

        double value;

        try {
            value = Double.parseDouble(arguments.get(1));
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "Invalid Number Format.");
            return;
        }

        if (value < 0) {
            sender.sendMessage(ChatColor.RED + "Value must be at least 0.");
            return;
        }

        plugin.getConfig().getConfigurationSection("islandTypeWeights").set(type, value);
        plugin.saveConfig();
        sender.sendMessage(ChatColor.GREEN + "Success");
    }

    @Override
    public List<String> getOptions(List<String> arguments) {
        List<String> options = new ArrayList<>();

        if (arguments.size() == 1)
            for (IslandType islandType : IslandType.values())
                options.add(islandType.toString());
        else if (arguments.size() == 2)
            options.add("<value>");

        return options;
    }
}
