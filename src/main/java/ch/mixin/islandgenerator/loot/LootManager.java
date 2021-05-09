package ch.mixin.islandgenerator.loot;

import ch.mixin.islandgenerator.helperClasses.Functions;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class LootManager {
    protected static final ArrayList<LootEntry> lootEntries;
    protected static final HashMap<Material, Double> materialWeights;
    protected static final HashMap<Material, Double> materialAmounts;

    static {
        lootEntries = new ArrayList<>();
        lootEntries.add(new LootEntry(Material.COAL, 1, 4));
        lootEntries.add(new LootEntry(Material.IRON_INGOT, 1, 1));
        lootEntries.add(new LootEntry(Material.GOLD_INGOT, 1, 0.5));
        lootEntries.add(new LootEntry(Material.REDSTONE, 0.5, 1));
        lootEntries.add(new LootEntry(Material.LAPIS_LAZULI, 0.5, 1));
        lootEntries.add(new LootEntry(Material.EMERALD, 0.5, 0.2));
        lootEntries.add(new LootEntry(Material.DIAMOND, 1, 0.1));

        lootEntries.add(new LootEntry(Material.FLINT, 0.4, 2));
        lootEntries.add(new LootEntry(Material.QUARTZ, 0.4, 4));
        lootEntries.add(new LootEntry(Material.CLAY_BALL, 0.4, 10));
        lootEntries.add(new LootEntry(Material.GLOWSTONE_DUST, 0.4, 4));
        lootEntries.add(new LootEntry(Material.BLAZE_ROD, 0.4, 0.4));
        lootEntries.add(new LootEntry(Material.ENDER_EYE, 0.4, 0.4));
        lootEntries.add(new LootEntry(Material.LEATHER, 0.4, 4));
        lootEntries.add(new LootEntry(Material.PHANTOM_MEMBRANE, 0.2, 1));

        lootEntries.add(new LootEntry(Material.ELYTRA, 0.1, 0.1));
        lootEntries.add(new LootEntry(Material.SADDLE, 0.1, 0.1));

        lootEntries.add(new LootEntry(Material.APPLE, 0.4, 3));
        lootEntries.add(new LootEntry(Material.GOLDEN_APPLE, 0.4, 0.1));

        lootEntries.add(new LootEntry(Material.SAND, 0.4, 10));
        lootEntries.add(new LootEntry(Material.DIRT, 0.4, 10));
        lootEntries.add(new LootEntry(Material.COBBLESTONE, 0.4, 10));
        lootEntries.add(new LootEntry(Material.OAK_WOOD, 0.4, 10));
        lootEntries.add(new LootEntry(Material.WHITE_WOOL, 0.4, 10));
        lootEntries.add(new LootEntry(Material.OBSIDIAN, 0.4, 0.4));

        lootEntries.add(new LootEntry(Material.WHEAT_SEEDS, 0.1, 3));
        lootEntries.add(new LootEntry(Material.BEETROOT_SEEDS, 0.1, 3));
        lootEntries.add(new LootEntry(Material.POTATO, 0.1, 3));
        lootEntries.add(new LootEntry(Material.CARROT, 0.1, 3));
        lootEntries.add(new LootEntry(Material.PUMPKIN_SEEDS, 0.1, 1));
        lootEntries.add(new LootEntry(Material.MELON_SEEDS, 0.1, 1));
        lootEntries.add(new LootEntry(Material.COCOA_BEANS, 0.1, 3));
        lootEntries.add(new LootEntry(Material.BAMBOO_SAPLING, 0.1, 1));
        lootEntries.add(new LootEntry(Material.SUGAR_CANE, 0.1, 3));
        lootEntries.add(new LootEntry(Material.BROWN_MUSHROOM, 0.1, 3));
        lootEntries.add(new LootEntry(Material.RED_MUSHROOM, 0.1, 3));
        lootEntries.add(new LootEntry(Material.NETHER_WART, 0.1, 0.5));

        lootEntries.add(new LootEntry(Material.ACACIA_SAPLING, 0.1, 2));
        lootEntries.add(new LootEntry(Material.BIRCH_SAPLING, 0.1, 2));
        lootEntries.add(new LootEntry(Material.DARK_OAK_SAPLING, 0.1, 2));
        lootEntries.add(new LootEntry(Material.JUNGLE_SAPLING, 0.1, 2));
        lootEntries.add(new LootEntry(Material.OAK_SAPLING, 0.1, 2));
        lootEntries.add(new LootEntry(Material.SPRUCE_SAPLING, 0.1, 2));

        lootEntries.add(new LootEntry(Material.PIG_SPAWN_EGG, 0.1, 0.3));
        lootEntries.add(new LootEntry(Material.RABBIT_SPAWN_EGG, 0.1, 0.3));
        lootEntries.add(new LootEntry(Material.CHICKEN_SPAWN_EGG, 0.1, 0.3));
        lootEntries.add(new LootEntry(Material.SHEEP_SPAWN_EGG, 0.1, 0.3));
        lootEntries.add(new LootEntry(Material.COW_SPAWN_EGG, 0.1, 0.3));
        lootEntries.add(new LootEntry(Material.OCELOT_SPAWN_EGG, 0.1, 0.2));
        lootEntries.add(new LootEntry(Material.WOLF_SPAWN_EGG, 0.1, 0.2));
        lootEntries.add(new LootEntry(Material.MOOSHROOM_SPAWN_EGG, 0.05, 0.2));

        lootEntries.add(new LootEntry(Material.WATER_BUCKET, 0.1, 0.2));
        lootEntries.add(new LootEntry(Material.LAVA_BUCKET, 1.0, 0.2));

        materialWeights = new HashMap<>();
        materialAmounts = new HashMap<>();

        for (LootEntry lootEntry : lootEntries) {
            materialWeights.put(lootEntry.getMaterial(), lootEntry.getWeight());
            materialAmounts.put(lootEntry.getMaterial(), lootEntry.getAmount());
        }
    }

    public static HashMap<Material, Integer> collectLoot(double multiplier) {
        HashMap<Material, Double> articles = new HashMap<>();
        double remainder = 1;

        while (remainder > 0) {
            double part = new Random().nextDouble() * remainder + 0.01;
            part = Math.min(part, remainder);
            remainder -= part;
            Material material = Functions.getRandomWithWeights(materialWeights);

            if (articles.containsKey(material)) {
                articles.put(material, part + articles.get(material));
            } else {
                articles.put(material, part);
            }
        }

        HashMap<Material, Integer> lootSet = new HashMap<>();

        for (Material material : articles.keySet()) {
            lootSet.put(material, Math.max(1, (int) Math.ceil(multiplier * articles.get(material) * materialAmounts.get(material))));
        }

        return lootSet;
    }
}
