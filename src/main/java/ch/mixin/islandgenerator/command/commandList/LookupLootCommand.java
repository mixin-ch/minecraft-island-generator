package ch.mixin.islandgenerator.command.commandList;

import ch.mixin.islandgenerator.command.SubCommand;
import ch.mixin.islandgenerator.main.IslandGeneratorPlugin;
import ch.mixin.islandgenerator.metaData.IslandData;
import ch.mixin.islandgenerator.metaData.WorldData;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LookupLootCommand extends SubCommand {
    public LookupLootCommand(IslandGeneratorPlugin plugin) {
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

        if (!sender.hasPermission("islandGenerator.lookup")) {
            sender.sendMessage(ChatColor.RED + "You lack Permission.");
            return;
        }

        HashMap<String, WorldData> worldDataMap = plugin.getMetaData().getWorldDataMap();

        for (String worldName : worldDataMap.keySet()) {
            World world = plugin.getServer().getWorld(worldName);

            if (world == null) {
                sender.sendMessage(ChatColor.YELLOW + "World: '" + worldName + "' not found on Server.");
                continue;
            }

            WorldData worldData = worldDataMap.get(worldName);

            int islandNumber = 0;
            int lootedNumber = 0;

            for (IslandData islandData : worldData.getIslandDatas()) {
                islandNumber++;
                lootedNumber += (islandData.isLooted() ? 1 : 0);
            }

            int percent = (int) Math.round(lootedNumber / (double) islandNumber * 100);

            sender.sendMessage(ChatColor.GREEN + "World: '" + worldName + "' is " + percent + "% looted.");
        }
    }

    @Override
    public List<String> getOptions(List<String> arguments) {
        return new ArrayList<>();
    }
}
