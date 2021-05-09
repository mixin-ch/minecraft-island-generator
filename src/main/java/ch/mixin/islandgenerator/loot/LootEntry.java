package ch.mixin.islandgenerator.loot;

import org.bukkit.Material;

import java.util.Objects;

public class LootEntry {
    protected final Material material;
    protected final double weight;
    protected final double amount;

    public LootEntry(Material material, double weight, double amount) {
        this.material = material;
        this.weight = weight;
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LootEntry lootEntry = (LootEntry) o;
        return Double.compare(lootEntry.weight, weight) == 0 && Double.compare(lootEntry.amount, amount) == 0 && material == lootEntry.material;
    }

    @Override
    public int hashCode() {
        return Objects.hash(material, weight, amount);
    }

    public Material getMaterial() {
        return material;
    }

    public double getWeight() {
        return weight;
    }

    public double getAmount() {
        return amount;
    }
}
