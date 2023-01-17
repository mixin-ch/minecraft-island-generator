package ch.mixin.islandgenerator.islandGeneration.islandConstructor;

import ch.mixin.islandgenerator.helperClasses.Functions;
import ch.mixin.islandgenerator.islandGeneration.IslandType;
import org.bukkit.Material;
import org.bukkit.TreeType;

import java.util.HashMap;
import java.util.Random;

public class IslandConstructorPremise {
    private IslandType islandType;
    private HashMap<Material, Double> blockTypesBot;
    private HashMap<Material, Double> blockTypesTop;
    private HashMap<Material, Double> blockTypesMid;
    private double treeFrequency;
    private HashMap<TreeType, Double> treeWeights;
    private double cactusFrequency;

    public IslandConstructorPremise(IslandType islandType) {
        if (islandType == null)
            islandType = IslandType.Grass;

        switch (islandType) {
            case Grass:
                premiseGrass();
                break;
            case Sand:
                premiseSand();
                break;
            case Snow:
                premiseSnow();
                break;
            case Ice:
                premiseIce();
                break;
            case Ore:
                premiseOre();
                break;
            case Nether:
                premiseNether();
                break;
            case End:
                premiseEnd();
                break;
            case Gravel:
                premiseGravel();
                break;
            case Clay:
                premiseClay();
                break;
            case Glass:
                premiseGlass();
                break;
        }
        if (blockTypesMid == null)
            blockTypesMid = new HashMap<>();
        if (blockTypesMid.isEmpty())
            blockTypesMid.put(Material.DIRT, 1.0);
        if (blockTypesBot == null)
            blockTypesBot = new HashMap<>();
        if (blockTypesTop == null)
            blockTypesTop = new HashMap<>();
        if (treeWeights == null)
            treeWeights = new HashMap<>();
        if (treeWeights.isEmpty())
            treeWeights.put(TreeType.TREE, 1.0);
    }

    private void premiseGrass() {
        blockTypesMid = new HashMap<>();
        blockTypesMid.put(Material.DIRT, 1.0);
        blockTypesTop = new HashMap<>();
        blockTypesTop.put(Material.GRASS_BLOCK, 1.0);

        treeFrequency = 0.05 * new Random().nextDouble();

        HashMap<TreeType, Double> treeWeightsPicker = new HashMap<>();
        treeWeightsPicker.put(TreeType.TREE, 1.0);
        treeWeightsPicker.put(TreeType.BIG_TREE, 0.25);
        treeWeightsPicker.put(TreeType.REDWOOD, 1.0);
        treeWeightsPicker.put(TreeType.TALL_REDWOOD, 0.25);
        treeWeightsPicker.put(TreeType.BIRCH, 1.0);
        treeWeightsPicker.put(TreeType.JUNGLE, 0.25);
        treeWeightsPicker.put(TreeType.SMALL_JUNGLE, 0.25);
        treeWeightsPicker.put(TreeType.COCOA_TREE, 0.25);
        treeWeightsPicker.put(TreeType.JUNGLE_BUSH, 0.25);
        treeWeightsPicker.put(TreeType.SWAMP, 0.5);
        treeWeightsPicker.put(TreeType.ACACIA, 1.0);
        treeWeightsPicker.put(TreeType.DARK_OAK, 0.5);
        treeWeightsPicker.put(TreeType.MEGA_REDWOOD, 0.125);
        treeWeightsPicker.put(TreeType.TALL_BIRCH, 0.25);

        treeWeights = pickWeights(treeWeightsPicker, 1);
    }

    private void premiseSand() {
        cactusFrequency = 0.2 * new Random().nextDouble();

        blockTypesMid = new HashMap<>();
        blockTypesBot = new HashMap<>();

        if (new Random().nextDouble() < 0.5) {
            blockTypesMid.put(Material.SAND, 1.0);
            blockTypesBot.put(Material.SANDSTONE, 1.0);
        } else {
            blockTypesMid.put(Material.RED_SAND, 1.0);
            blockTypesBot.put(Material.RED_SANDSTONE, 1.0);
        }
    }

    private void premiseSnow() {
        blockTypesMid = new HashMap<>();

        if (new Random().nextDouble() < 0.5) {
            blockTypesMid.put(Material.SNOW_BLOCK, 1.0);
        } else {
            blockTypesMid.put(Material.POWDER_SNOW, 1.0);
        }
    }

    private void premiseIce() {
        blockTypesMid = new HashMap<>();

        if (new Random().nextDouble() < 0.5) {
            blockTypesMid.put(Material.PACKED_ICE, 1.0);
        } else {
            blockTypesMid.put(Material.BLUE_ICE, 1.0);
        }
    }

    private void premiseOre() {
        HashMap<Material, Double> blockWeightsPicker = new HashMap<>();
        blockWeightsPicker.put(Material.COAL_ORE, 16.0);
        blockWeightsPicker.put(Material.IRON_ORE, 16.0);
        blockWeightsPicker.put(Material.GOLD_ORE, 8.0);
        blockWeightsPicker.put(Material.DIAMOND_ORE, 1.0);
        blockWeightsPicker.put(Material.ANCIENT_DEBRIS, 0.5);
        blockWeightsPicker.put(Material.EMERALD_ORE, 2.0);
        blockWeightsPicker.put(Material.REDSTONE_ORE, 4.0);
        blockWeightsPicker.put(Material.LAPIS_ORE, 4.0);
        blockWeightsPicker.put(Material.COPPER_ORE, 2.0);
        blockWeightsPicker.put(Material.BUDDING_AMETHYST, 1.0);

        int numberOres = 3;

        blockTypesMid = pickWeights(blockWeightsPicker, numberOres);
        blockTypesMid.put(Material.STONE, 100.0 * numberOres);
    }

    private void premiseNether() {
        blockTypesMid = new HashMap<>();
        blockTypesMid.put(Material.NETHERRACK, 1.0);

        Random random = new Random();

        if (random.nextBoolean()) {
            blockTypesMid.put(Material.NETHER_QUARTZ_ORE, 0.05 * random.nextDouble());
        } else {
            blockTypesMid.put(Material.GLOWSTONE, 0.05 * random.nextDouble());
        }
        blockTypesMid.put(Material.MAGMA_BLOCK, 0.05 * random.nextDouble());

        blockTypesTop = new HashMap<>();
        int topType = random.nextInt(4);
        boolean hasTrees = false;
        HashMap<TreeType, Double> treeWeightsPicker = new HashMap<>();

        switch (topType) {
            case 0:
                blockTypesTop.put(Material.FIRE, 1.0);
                break;
            case 1:
                blockTypesTop.put(Material.SOUL_SAND, 1.0);
                break;
            case 2:
                blockTypesTop.put(Material.CRIMSON_NYLIUM, 1.0);
                hasTrees = true;
                treeWeightsPicker.put(TreeType.CRIMSON_FUNGUS, 1.0);
                break;
            case 3:
                blockTypesTop.put(Material.WARPED_NYLIUM, 1.0);
                hasTrees = true;
                treeWeightsPicker.put(TreeType.WARPED_FUNGUS, 1.0);
                break;
        }

        if (hasTrees) {
            treeFrequency = 0.05 * new Random().nextDouble();
            treeWeights = treeWeightsPicker;
        }
    }

    private void premiseEnd() {
        blockTypesMid = new HashMap<>();
        blockTypesMid.put(Material.END_STONE, 1.0);
    }

    private void premiseGravel() {
        blockTypesMid = new HashMap<>();
        blockTypesMid.put(Material.GRAVEL, 1.0);
        blockTypesMid.put(Material.COAL_BLOCK, 0.01);
        blockTypesBot = new HashMap<>();
        blockTypesBot.put(Material.STONE, 1.0);
    }

    private void premiseClay() {
        blockTypesMid = new HashMap<>();
        blockTypesMid.put(Material.CLAY, 1.0);
        blockTypesMid.put(Material.LAPIS_BLOCK, 0.001);
    }

    private void premiseGlass() {
        blockTypesMid = new HashMap<>();
        blockTypesMid.put(Material.GLASS, 1.0);
        blockTypesMid.put(Material.QUARTZ_BLOCK, 0.001);
    }

    private <T> HashMap<T, Double> pickWeights(HashMap<T, Double> treeWeightsPicker, int amount) {
        HashMap<T, Double> weights = new HashMap<>();

        for (int i = 0; i < amount; i++) {
            T t = Functions.getRandomWithWeights(treeWeightsPicker);

            if (!weights.containsKey(t))
                weights.put(t, 1.0);

            weights.put(t, 1 + weights.get(t));
        }

        return weights;
    }

    public IslandType getIslandType() {
        return islandType;
    }

    public HashMap<Material, Double> getBlockTypesMid() {
        return blockTypesMid;
    }

    public HashMap<Material, Double> getBlockTypesBot() {
        return blockTypesBot;
    }

    public HashMap<Material, Double> getBlockTypesTop() {
        return blockTypesTop;
    }

    public double getTreeFrequency() {
        return treeFrequency;
    }

    public HashMap<TreeType, Double> getTreeWeights() {
        return treeWeights;
    }

    public double getCactusFrequency() {
        return cactusFrequency;
    }
}
