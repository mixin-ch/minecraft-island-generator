package ch.mixin.islandgenerator.eventListener;

import ch.mixin.islandgenerator.loot.LootManager;
import ch.mixin.islandgenerator.main.IslandGeneratorPlugin;
import ch.mixin.islandgenerator.metaData.IslandData;
import ch.mixin.islandgenerator.metaData.MetaData;
import ch.mixin.islandgenerator.metaData.WorldData;
import ch.mixin.islandgenerator.model.Coordinate3D;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class EventListener implements Listener {
    private final IslandGeneratorPlugin plugin;

    public EventListener(IslandGeneratorPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void manipulate(PlayerArmorStandManipulateEvent e) {
        if (!e.getRightClicked().isVisible()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void manipulate(PlayerInteractEvent e) {
        Block block = e.getClickedBlock();

        if (block == null) {
            return;
        }

        if (!block.getType().equals(Material.CHEST)) {
            return;
        }

        World world = block.getWorld();
        String worldName = world.getName();
        MetaData metaData = plugin.getMetaData();
        HashMap<String, WorldData> worldDataMap = metaData.getWorldDataMap();

        if (!worldDataMap.containsKey(worldName)) {
            return;
        }

        ArrayList<IslandData> islandDatas = worldDataMap.get(worldName).getIslandDatas();
        Location location = block.getLocation();
        Coordinate3D blockCoordinate = Coordinate3D.toCoordinate(location);

        IslandData islandData = null;

        for (IslandData id : islandDatas) {
            if (id.isLooted())
                continue;

            if (id.getLootPosition().equals(blockCoordinate)) {
                islandData = id;
                break;
            }
        }

        if (islandData == null)
            return;

        islandData.setLooted(true);
        metaData.save();
        block.setType(Material.AIR);
        Location locationLoot = blockCoordinate.sum(0, 1, 0).toLocation(world);
        locationLoot.add(0.5, 0.5, 0.5);
        HashMap<Material, Integer> lootSet = LootManager.collectLoot(plugin.getConfig().getInt("lootMultiplier"));

        for (Material material : lootSet.keySet()) {
            world.dropItem(locationLoot, new ItemStack(material, lootSet.get(material)));
        }
    }
}
