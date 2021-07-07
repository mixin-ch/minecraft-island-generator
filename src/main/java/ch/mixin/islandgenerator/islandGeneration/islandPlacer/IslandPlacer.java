package ch.mixin.islandgenerator.islandGeneration.islandPlacer;

import ch.mixin.islandgenerator.helperClasses.Functions;
import ch.mixin.islandgenerator.main.IslandGeneratorPlugin;
import ch.mixin.islandgenerator.helperClasses.Constants;
import ch.mixin.islandgenerator.islandGeneration.islandConstructor.IslandBlueprint;
import ch.mixin.islandgenerator.metaData.MetaData;
import ch.mixin.islandgenerator.model.Coordinate3D;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;

import java.util.Map;

public class IslandPlacer {
    private final IslandGeneratorPlugin plugin;

    public IslandPlacer(IslandGeneratorPlugin plugin) {
        this.plugin = plugin;
    }

    public void placeIsland(IslandBlueprint islandBlueprint) {
        World world = islandBlueprint.getWorld();
        Functions.makeHolographicText(islandBlueprint.getNames(), islandBlueprint.getNameLocation().toLocation(world));

        placeLoot(world, islandBlueprint.getLootPosition());

        for (Map.Entry<Coordinate3D, Material> entry : islandBlueprint.getBlockMap()) {
            placeBlock(world, entry.getKey(), entry.getValue());
        }

        for (Map.Entry<Coordinate3D, TreeType> entry : islandBlueprint.getTreeMap()) {
            placeTree(world, entry.getKey(), entry.getValue());
        }

        for (Coordinate3D entry : islandBlueprint.getCactusList()) {
            placeCactus(world, entry);
        }
    }

    private void placeLoot(World world, Coordinate3D coordinate3D) {
        Location location = coordinate3D.toLocation(world);

        if (!Constants.Airs.contains(location.getBlock().getType()))
            return;

        location.getBlock().setType(Material.CHEST, true);
    }

    private void placeBlock(World world, Coordinate3D coordinate3D, Material material) {
        Location location = coordinate3D.toLocation(world);

        if (!Constants.Airs.contains(location.getBlock().getType()))
            return;

        location.getBlock().setType(material, false);
    }

    private void placeTree(World world, Coordinate3D coordinate3D, TreeType treeType) {
        Location location = coordinate3D.toLocation(world);

        if (!Constants.Airs.contains(location.getBlock().getType()))
            return;

        world.generateTree(location, treeType);
    }

    private void placeCactus(World world, Coordinate3D coordinate3D) {
        Location location = coordinate3D.toLocation(world);

        if (!Constants.Airs.contains(location.getBlock().getType()))
            return;

        Material materialBelow = Functions.offset(location, -1).getBlock().getType();

        if (materialBelow != Material.CACTUS && materialBelow != Material.SAND && materialBelow != Material.RED_SAND)
            return;

        for (Coordinate3D neighbour : coordinate3D.neighboursDirect2D()) {
            if (!Constants.Airs.contains(neighbour.toLocation(world).getBlock().getType())) {
                return;
            }
        }

        location.getBlock().setType(Material.CACTUS, false);
    }
}

