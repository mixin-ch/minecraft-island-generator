package ch.mixin.islandgenerator.command;

import ch.mixin.islandgenerator.main.IslandGeneratorPlugin;

public class CommandInitializer {
    public static void setupCommands(IslandGeneratorPlugin plugin) {
        RootCommand rootCommand = new RootCommand(plugin, "mx-ig");
        plugin.getCommand("mx-ig").setExecutor(rootCommand);
        plugin.getCommand("mx-ig").setTabCompleter(new RootCompleter(rootCommand));
    }
}
