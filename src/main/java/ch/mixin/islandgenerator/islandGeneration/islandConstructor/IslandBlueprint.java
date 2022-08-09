package ch.mixin.islandgenerator.islandGeneration.islandConstructor;

import ch.mixin.islandgenerator.model.Coordinate3D;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.Map;

public class IslandBlueprint {
    private final World world;
    private final Coordinate3D center;
    private final Coordinate3D weightCenter;
    private final double weightRadius;
    private final Coordinate3D lootPosition;
    private final ArrayList<Map.Entry<Coordinate3D, Material>> blockMap;
    private final ArrayList<Map.Entry<Coordinate3D, TreeType>> treeMap;
    private final ArrayList<Coordinate3D> cactusList;
    private final ArrayList<String> names;
    private final Coordinate3D nameLocation;

    public IslandBlueprint(World world, Coordinate3D center, Coordinate3D weightCenter, double weightRadius, Coordinate3D lootPosition, ArrayList<Map.Entry<Coordinate3D, Material>> blockMap, ArrayList<Map.Entry<Coordinate3D, TreeType>> treeMap, ArrayList<Coordinate3D> cactusList, ArrayList<String> names, Coordinate3D nameLocation) {
        this.world = world;
        this.center = center;
        this.weightCenter = weightCenter;
        this.weightRadius = weightRadius;
        this.lootPosition = lootPosition;
        this.blockMap = blockMap;
        this.treeMap = treeMap;
        this.cactusList = cactusList;
        this.names = names;
        this.nameLocation = nameLocation;
    }

    public World getWorld() {
        return world;
    }

    public Coordinate3D getCenter() {
        return center;
    }

    public Coordinate3D getWeightCenter() {
        return weightCenter;
    }

    public double getWeightRadius() {
        return weightRadius;
    }

    public Coordinate3D getLootPosition() {
        return lootPosition;
    }

    public ArrayList<Map.Entry<Coordinate3D, Material>> getBlockMap() {
        return blockMap;
    }

    public ArrayList<Map.Entry<Coordinate3D, TreeType>> getTreeMap() {
        return treeMap;
    }

    public ArrayList<Coordinate3D> getCactusList() {
        return cactusList;
    }

    public ArrayList<String> getNames() {
        return names;
    }

    public Coordinate3D getNameLocation() {
        return nameLocation;
    }
}
