package ch.mixin.islandgenerator.command.commandList;

import ch.mixin.islandgenerator.command.SubCommand;
import ch.mixin.islandgenerator.main.IslandGeneratorPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class ReloadCommand extends SubCommand {
    public ReloadCommand(IslandGeneratorPlugin plugin) {
        super(plugin);
    }

    @Override
    public SubCommand fetchCommand(List<String> arguments) {
        return this;
    }

    @Override
    public void execute(CommandSender sender, List<String> arguments) {
        if (arguments.size() != 0) {
            sender.sendMessage(ChatColor.RED + "Invalid Argument Number.");
            return;
        }

        if (!sender.hasPermission("islandGenerator.reload")) {
            sender.sendMessage(ChatColor.RED + "You lack Permission.");
            return;
        }

        plugin.reload();
        sender.sendMessage(ChatColor.GREEN + "Success");
    }

    @Override
    public List<String> getOptions(List<String> arguments) {
        return new ArrayList<>();
    }
}
