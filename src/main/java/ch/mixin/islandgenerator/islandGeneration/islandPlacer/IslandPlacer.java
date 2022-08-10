package ch.mixin.islandgenerator.islandGeneration.islandPlacer;

import ch.mixin.islandgenerator.helperClasses.Functions;
import ch.mixin.islandgenerator.main.IslandGeneratorPlugin;
import ch.mixin.islandgenerator.helperClasses.Constants;
import ch.mixin.islandgenerator.islandGeneration.islandConstructor.IslandBlueprint;
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

        for (Map.Entry<Coordinate3D, Material> entry : islandBlueprint.getBlockMap())
            placeBlock(world, entry.getKey(), entry.getValue());

        placeGlassSphere(world, islandBlueprint);

        for (Map.Entry<Coordinate3D, TreeType> entry : islandBlueprint.getTreeMap())
            placeTree(world, entry.getKey(), entry.getValue());

        for (Coordinate3D entry : islandBlueprint.getCactusList())
            placeCactus(world, entry);
    }

    private void placeGlassSphere(World world, IslandBlueprint islandBlueprint) {
        Coordinate3D center = islandBlueprint.getCenter();
        int glassSphereMaximumHeight = plugin.getConfig().getInt("glassSphereMaximumHeight");
        int glassSphereMinimumHeight = plugin.getConfig().getInt("glassSphereMinimumHeight");

        if (center.getY() > glassSphereMaximumHeight || center.getY() < glassSphereMinimumHeight)
            return;

        Coordinate3D weightCenter = islandBlueprint.getWeightCenter();
        int sphereRadius = (int) Math.ceil(islandBlueprint.getWeightRadius() + 1);

        int minX = -sphereRadius + weightCenter.getX();
        int maxX = sphereRadius + weightCenter.getX();
        int minY = Math.max(world.getMaxHeight(), weightCenter.getY() - sphereRadius);
        int maxY = Math.min(world.getMinHeight(), weightCenter.getY() + sphereRadius);
        int minZ = -sphereRadius + weightCenter.getZ();
        int maxZ = sphereRadius + weightCenter.getZ();

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    Coordinate3D coordinate3D = new Coordinate3D(x, y, z);
                    double length = coordinate3D.distance(weightCenter);

                    if (length > sphereRadius)
                        continue;

                    Location location = coordinate3D.toLocation(world);
                    Material blockType = location.getBlock().getType();

                    if (!Constants.Airs.contains(blockType) && blockType != Material.WATER)
                        continue;

                    if (sphereRadius - length < 1)
                        location.getBlock().setType(Material.GLASS);
                    else if (coordinate3D.getY() > center.getY())
                        location.getBlock().setType(Material.AIR);
                }
            }
        }
    }

    private void placeLoot(World world, Coordinate3D coordinate3D) {
        Location location = coordinate3D.toLocation(world);
        Material blockType = location.getBlock().getType();

        if (!Constants.Airs.contains(blockType) && blockType != Material.WATER)
            return;

        location.getBlock().setType(Material.CHEST);
    }

    private void placeBlock(World world, Coordinate3D coordinate3D, Material material) {
        Location location = coordinate3D.toLocation(world);
        Material blockType = location.getBlock().getType();

        if (!Constants.Airs.contains(blockType) && blockType != Material.WATER)
            return;

        location.getBlock().setType(material);
    }

    private void placeTree(World world, Coordinate3D coordinate3D, TreeType treeType) {
        Location location = coordinate3D.toLocation(world);
        Material blockType = location.getBlock().getType();

        if (!Constants.Airs.contains(blockType) && blockType != Material.WATER)
            return;

        world.generateTree(location, treeType);
    }

    private void placeCactus(World world, Coordinate3D coordinate3D) {
        Location location = coordinate3D.toLocation(world);
        Material blockType = location.getBlock().getType();

        if (!Constants.Airs.contains(blockType) && blockType != Material.WATER)
            return;

        Material materialBelow = Functions.offset(location, -1).getBlock().getType();

        if (materialBelow != Material.CACTUS && materialBelow != Material.SAND && materialBelow != Material.RED_SAND)
            return;

        for (Coordinate3D neighbour : coordinate3D.neighboursDirect2D()) {
            if (!Constants.Airs.contains(neighbour.toLocation(world).getBlock().getType())) {
                return;
            }
        }

        location.getBlock().setType(Material.CACTUS);
    }
}

