package ch.mixin.islandgenerator.loot;

import ch.mixin.islandgenerator.helperClasses.Functions;
import ch.mixin.islandgenerator.islandGeneration.islandConstructor.IslandConstructor;
import ch.mixin.islandgenerator.islandGeneration.islandPlacer.IslandPlacer;
import ch.mixin.islandgenerator.main.IslandGeneratorPlugin;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class LootManager {
    private final IslandGeneratorPlugin plugin;

    public LootManager(IslandGeneratorPlugin plugin) {
        this.plugin = plugin;
        initialize();
    }

    private HashMap<Material, Double> materialWeights;
    private HashMap<Material, Double> materialAmounts;

    private void initialize() {
        ConfigurationSection lootTableSection = plugin.getConfig().getConfigurationSection("lootTable");
        materialWeights = new HashMap<>();
        materialAmounts = new HashMap<>();

        if (lootTableSection == null)
            return;

        for (String materialName : lootTableSection.getKeys(false)) {
            Material material = Material.getMaterial(materialName.toUpperCase());

            if (material == null)
                continue;

            ConfigurationSection lootEntrySection = lootTableSection.getConfigurationSection(materialName);
            materialWeights.put(material, lootEntrySection.getDouble("weight"));
            materialAmounts.put(material, lootEntrySection.getDouble("amount"));
        }
    }

    public HashMap<Material, Integer> collectLoot(double multiplier) {
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
