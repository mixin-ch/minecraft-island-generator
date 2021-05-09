package ch.mixin.islandgenerator.helperClasses;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.line.TextLine;
import ch.mixin.islandgenerator.main.IslandGeneratorPlugin;
import ch.mixin.islandgenerator.model.Coordinate2D;
import ch.mixin.islandgenerator.model.Coordinate3D;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Functions {
    public static int random(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }

    public static <T> T getRandomWithWeights(HashMap<T, Double> weights) {
        double sum = 0;

        for (double weight : weights.values()) {
            sum += weight;
        }

        double pointer = new Random().nextDouble() * sum;

        for (T t : weights.keySet()) {
            pointer -= weights.get(t);

            if (pointer <= 0)
                return t;
        }

        return null;
    }

    public static ArrayList<Coordinate3D> getSphere3D(Coordinate3D center, double radius) {
        ArrayList<Coordinate3D> sphere = new ArrayList<>();
        int range = (int) Math.round(Math.ceil(radius));

        for (int x = -range; x <= range; x++) {
            for (int y = -range; y <= range; y++) {
                for (int z = -range; z <= range; z++) {
                    Coordinate3D c3d = new Coordinate3D(x, y, z);
                    if (c3d.length() <= radius) {
                        sphere.add(center.sum(c3d));
                    }
                }
            }
        }

        return sphere;
    }

    public static ArrayList<Coordinate2D> getSphere2D(Coordinate2D center, double radius) {
        ArrayList<Coordinate2D> sphere = new ArrayList<>();
        int range = (int) Math.round(Math.ceil(radius));

        for (int x = -range; x <= range; x++) {
            for (int z = -range; z <= range; z++) {
                Coordinate2D c2d = new Coordinate2D(x, z);
                if (c2d.length() <= radius) {
                    sphere.add(center.sum(c2d));
                }
            }
        }

        return sphere;
    }

    public static ArrayList<Coordinate2D> getCircle2D(Coordinate2D center, double radius) {
        ArrayList<Coordinate2D> circle = new ArrayList<>();
        int range = (int) Math.round(Math.ceil(radius));

        for (int x = -range; x <= range; x++) {
            for (int z = -range; z <= range; z++) {
                Coordinate2D c2d = new Coordinate2D(x, z);
                double difference = Math.abs(radius - c2d.length());

                if (difference <= 0.5) {
                    circle.add(center.sum(c2d));
                }
            }
        }

        return circle;
    }

    public static <T> ArrayList<T> merge(ArrayList<T> list1, ArrayList<T> list2) {
        ArrayList<T> combined = new ArrayList<>(list1);
        combined.removeAll(list2);
        combined.addAll(list2);
        return combined;
    }

    public static Location offset(Location location, int offsetY) {
        return offset(location, 0, offsetY, 0);
    }

    public static Location offset(Location location, int offsetX, int offsetY, int offsetZ) {
        if (location == null)
            return null;

        int x = location.getBlockX() + offsetX;
        int y = location.getBlockY() + offsetY;
        int z = location.getBlockZ() + offsetZ;

        World world = location.getWorld();

        if (y < 0 || y > world.getMaxHeight())
            return null;

        return new Location(world, x, y, z);
    }

    public static void makeHolographicText(ArrayList<String> names, Location location) {
        if (IslandGeneratorPlugin.useHolographicDisplays) {
            Hologram hologram = HologramsAPI.createHologram(IslandGeneratorPlugin.PLUGIN, location);
            for (String name : names) {
                TextLine t = hologram.appendTextLine(name);
            }

            return;
        }

//        World world = location.getWorld();
//        double x = location.getX();
//        double y = location.getY();
//        double z = location.getZ();
//
//        y += 0.25 * names.size();
//
//        for (String name : names) {
//            y -= 0.25;
//            Location l = new Location(world, x, y, z);
//            ArmorStand as = (ArmorStand) l.getWorld().spawnEntity(l, EntityType.ARMOR_STAND); //Spawn the ArmorStand
//
//            as.setGravity(false); //Make sure it doesn't fall
//            as.setCanPickupItems(false); //I'm not sure what happens if you leave this as it is, but you might as well disable it
//            as.setCustomName(name); //Set this to the text you want
//            as.setCustomNameVisible(true); //This makes the text appear no matter if your looking at the entity or not
//            as.setVisible(false); //Makes the ArmorStand invisible
//        }
    }
}
