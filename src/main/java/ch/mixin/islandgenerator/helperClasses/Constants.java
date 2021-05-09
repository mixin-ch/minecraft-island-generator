package ch.mixin.islandgenerator.helperClasses;

import org.bukkit.Material;

import java.util.ArrayList;

public class Constants {
    public static final ArrayList<Material> Airs;

    static {
        Airs = new ArrayList<>();
        Airs.add(Material.AIR);
        Airs.add(Material.CAVE_AIR);
        Airs.add(Material.VOID_AIR);
    }
}
