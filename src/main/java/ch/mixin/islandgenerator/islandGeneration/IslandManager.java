package ch.mixin.islandgenerator.islandGeneration;

import ch.mixin.islandgenerator.helperClasses.Functions;
import ch.mixin.islandgenerator.islandGeneration.islandPlacer.IslandPlacer;
import ch.mixin.islandgenerator.metaData.IslandData;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import ch.mixin.islandgenerator.helperClasses.Constants;
import ch.mixin.islandgenerator.islandGeneration.islandConstructor.IslandConstructor;
import ch.mixin.islandgenerator.main.IslandGeneratorPlugin;
import ch.mixin.islandgenerator.metaData.MetaData;
import ch.mixin.islandgenerator.metaData.WorldData;
import ch.mixin.islandgenerator.model.Coordinate3D;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.*;

public class IslandManager {
    private final IslandGeneratorPlugin plugin;
    private final IslandConstructor islandConstructor;
    private final IslandPlacer islandPlacer;
    private boolean isActive = false;

    public IslandManager(IslandGeneratorPlugin plugin) {
        this.plugin = plugin;
        islandConstructor = new IslandConstructor(plugin);
        islandPlacer = new IslandPlacer(plugin);
    }

    public void startIslandRegeneration() {
        if (isActive)
            return;

        isActive = true;
        consolePrint("Start Island Regeneration");
        HashMap<String, WorldData> worldDataMap = plugin.getMetaData().getWorldDataMap();
        HashMap<World, ArrayList<IslandData>> islandDataMap = new HashMap<>();
        List<String> worldNames = IslandGeneratorPlugin.PLUGIN.getConfig().getStringList("worlds");

        for (String worldName : worldNames) {
            World world = plugin.getServer().getWorld(worldName);

            if (world == null)
                continue;

            consolePrint("Start Island Finding: " + worldName);
            ArrayList<IslandData> islandDataList = new ArrayList<>();
            islandDataMap.put(world, islandDataList);

            WorldData worldData = worldDataMap.get(worldName);

            if (worldData == null) {
                worldData = new WorldData(0, new ArrayList<>());
                worldDataMap.put(worldName, worldData);
            }

            for (IslandData islandData : worldData.getIslandDatas()) {
                if (!islandData.isLooted()) {
                    islandDataList.add(islandData);
                }
            }

            consolePrint("Finish Island Finding: " + worldName);
            consolePrint("+" + islandDataList.size() + " old Islands");
        }

        consolePrint("Start Island Reconstruction");
        regenerationStep(islandDataMap);
    }

    public void startIslandGeneration() {
        if (isActive)
            return;

        isActive = true;
        consolePrint("Start Island Generation");
        HashMap<String, WorldData> worldDataMap = plugin.getMetaData().getWorldDataMap();
        Random random = plugin.getRandom();
        HashMap<World, ArrayList<IslandData>> islandDataMap = new HashMap<>();
        List<String> worldNames = plugin.getConfig().getStringList("worlds");

        int maximumHeight = plugin.getConfig().getInt("maximumHeight");
        int minimumHeight = plugin.getConfig().getInt("minimumHeight");
        int centerHeight = (maximumHeight - minimumHeight) / 2;

        int spawnRadius = plugin.getConfig().getInt("spawnRadius");
        int islandDistance = plugin.getConfig().getInt("islandDistance");

        for (String worldName : worldNames) {
            World world = plugin.getServer().getWorld(worldName);

            if (world == null)
                continue;

            WorldData worldData = worldDataMap.get(worldName);

            if (worldData == null) {
                worldData = new WorldData(0, new ArrayList<>());
                worldDataMap.put(worldName, worldData);
            }

            consolePrint("Start Island Pointing: " + worldName);
            ArrayList<IslandData> newIslandDataList = new ArrayList<>();
            islandDataMap.put(world, newIslandDataList);
            int limit = worldData.getSpawnRadius();

            if (limit >= spawnRadius)
                continue;

            ArrayList<IslandData> islandDatas = worldData.getIslandDatas();
            int yMin = Math.max(minimumHeight, centerHeight - spawnRadius);
            int yMax = Math.min(maximumHeight, centerHeight + spawnRadius);

            int iterations = (int) (Math.pow(2 * spawnRadius + 1, 2) * (yMax - yMin + 1) / Math.pow(islandDistance, 3));
            consolePrint("Island Pointing: " + worldName + " x" + iterations);
            int percentile = 0;

            islandLoop:
            for (int i = 0; i < iterations; i++) {
                if ((percentile + 10) / 100.0 <= i / (double) iterations) {
                    percentile += 10;
                    consolePrint("Island Pointing: " + worldName + " " + percentile + "%");
                }

                int x = random.nextInt(spawnRadius + 1) * (random.nextBoolean() ? 1 : -1);
                int y = random.nextInt(yMax + 1 - yMin) + yMin;
                int z = random.nextInt(spawnRadius + 1) * (random.nextBoolean() ? 1 : -1);

                if (x < limit && x > -limit
                        && y < limit && y > -limit
                        && z < limit && z > -limit) {
                    continue;
                }

                Coordinate3D newIslandCenter = new Coordinate3D(x, y, z);
                for (IslandData islandData : islandDatas) {
                    if (newIslandCenter.distance(islandData.getIslandCenter()) < islandDistance)
                        continue islandLoop;
                }

                IslandData newIslandData = new IslandData(newIslandCenter, null, false, new ArrayList<>());
                islandDatas.add(newIslandData);
                newIslandDataList.add(newIslandData);
            }

            worldData.setSpawnRadius(spawnRadius);
            consolePrint("Finish Island Pointing: " + worldName);
            consolePrint("+" + newIslandDataList.size() + " new Islands");
        }

        consolePrint("Start Island Construction");
        generationStep(islandDataMap);
    }

    private void regenerationStep(HashMap<World, ArrayList<IslandData>> islandDataMap) {
        Optional<World> optional = islandDataMap.keySet().stream().findFirst();

        if (!optional.isPresent())
            return;

        World world = optional.get();
        ArrayList<IslandData> islandDataList = islandDataMap.get(world);

        if (islandDataList.size() > 0) {
            IslandData islandData = islandDataList.get(0);
            islandDataList.remove(islandData);

            for (Coordinate3D coordinate3D : Functions.getSphere3D(islandData.getIslandCenter(), plugin.getConfig().getInt("islandDistance") * 0.5)) {
                Block block = coordinate3D.toLocation(world).getBlock();

                if (!Constants.Airs.contains(block.getType())) {
                    block.setType(Material.AIR);
                }
            }

            islandPlacer.placeIsland(islandConstructor.constructIsland(world, islandData));
            consolePrint(islandDataList.size() + " islands left in " + world.getName() + ".");
        }

        if (islandDataList.size() == 0) {
            islandDataMap.remove(world);
            consolePrint(islandDataMap.size() + " worlds left.");
        }

        if (islandDataMap.size() > 0) {
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> regenerationStep(islandDataMap), plugin.getConfig().getInt("tickBuffer"));
        } else {
            plugin.getMetaData().save();
            regenerateHolograms();
            consolePrint("Finish Island Reconstruction");
            isActive = false;
        }
    }

    private void generationStep(HashMap<World, ArrayList<IslandData>> islandDataMap) {
        Optional<World> optional = islandDataMap.keySet().stream().findFirst();

        if (!optional.isPresent())
            return;

        World world = optional.get();
        ArrayList<IslandData> islandDataList = islandDataMap.get(world);

        if (islandDataList.size() > 0) {
            IslandData islandData = islandDataList.get(0);
            islandDataList.remove(islandData);
            islandPlacer.placeIsland(islandConstructor.constructIsland(world, islandData));
            consolePrint(islandDataList.size() + " islands left in " + world.getName() + ".");
        }

        if (islandDataList.size() == 0) {
            islandDataMap.remove(world);
            consolePrint(islandDataMap.size() + " worlds left.");
        }

        if (islandDataMap.size() > 0) {
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> generationStep(islandDataMap), plugin.getConfig().getInt("tickBuffer"));
        } else {
            plugin.getMetaData().save();
            consolePrint("Finish Island Construction");
            isActive = false;
        }
    }

    public void regenerateHolograms() {
        if (!IslandGeneratorPlugin.useHolographicDisplays)
            return;

        for (Hologram hologram : HologramsAPI.getHolograms(plugin)) {
            hologram.delete();
        }

        HashMap<String, WorldData> worldDataMap = plugin.getMetaData().getWorldDataMap();

        for (String worldName : worldDataMap.keySet()) {
            World world = plugin.getServer().getWorld(worldName);

            if (world == null)
                continue;
            for (IslandData islandData : worldDataMap.get(worldName).getIslandDatas()) {
                Functions.makeHolographicText(islandData.getNames(), islandData.getLootPosition().sum(0, 3, 0).toLocation(world));
            }
        }
    }

    private void consolePrint(String text) {
        System.out.println("[" + IslandGeneratorPlugin.PLUGIN_NAME + "] " + text);
    }
}
