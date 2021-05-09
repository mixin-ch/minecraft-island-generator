package ch.mixin.islandgenerator.command.commandList.config;

import ch.mixin.islandgenerator.command.SubCommand;
import ch.mixin.islandgenerator.main.IslandGeneratorPlugin;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class WorldsCommand extends SubCommand {
    public WorldsCommand(IslandGeneratorPlugin plugin) {
        super(plugin);
    }

    @Override
    public SubCommand fetchCommand(List<String> arguments) {
        return this;
    }

    @Override
    public void execute(CommandSender sender, List<String> arguments) {
        if (arguments.size() == 0) {
            sender.sendMessage(ChatColor.RED + "Invalid Argument Number.");
            return;
        }

        if (!sender.hasPermission("islandGenerator.config")) {
            sender.sendMessage(ChatColor.RED + "You lack Permission.");
            return;
        }

        plugin.getConfig().set("worlds", arguments);
        plugin.saveConfig();
        sender.sendMessage(ChatColor.GREEN + "Success");
    }

    @Override
    public List<String> getOptions(List<String> arguments) {
        List<String> options = new ArrayList<>();

        worldLoop:
        for (World world : plugin.getServer().getWorlds()) {
            String worldName = world.getName();

            for (String argument : arguments) {
                if (argument.equalsIgnoreCase(worldName))
                    continue worldLoop;
            }

            options.add(worldName);
        }

        return options;
    }
}
