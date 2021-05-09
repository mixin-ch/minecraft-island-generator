package ch.mixin.islandgenerator.command;

import ch.mixin.islandgenerator.main.IslandGeneratorPlugin;
import org.bukkit.command.CommandSender;

import java.util.List;

public abstract class SubCommand {
    protected final IslandGeneratorPlugin plugin;

    public SubCommand(IslandGeneratorPlugin plugin) {
        this.plugin = plugin;
    }

    public abstract SubCommand fetchCommand(List<String> arguments);

    public abstract void execute(CommandSender sender, List<String> arguments);

    public abstract List<String> getOptions(List<String> arguments);
}
