package ch.mixin.islandgenerator.metaData;

import ch.mixin.islandgenerator.model.Coordinate3D;

import java.util.ArrayList;

public class IslandData {
    private Coordinate3D islandCenter;
    private Coordinate3D lootPosition;
    private boolean looted;
    private ArrayList<String> names;

    public IslandData(Coordinate3D islandCenter, Coordinate3D lootPosition, boolean looted, ArrayList<String> names) {
        this.islandCenter = islandCenter;
        this.lootPosition = lootPosition;
        this.looted = looted;
        this.names = names;
    }

    public Coordinate3D getIslandCenter() {
        return islandCenter;
    }

    public void setIslandCenter(Coordinate3D islandCenter) {
        this.islandCenter = islandCenter;
    }

    public Coordinate3D getLootPosition() {
        return lootPosition;
    }

    public void setLootPosition(Coordinate3D lootPosition) {
        this.lootPosition = lootPosition;
    }

    public boolean isLooted() {
        return looted;
    }

    public void setLooted(boolean looted) {
        this.looted = looted;
    }

    public ArrayList<String> getNames() {
        return names;
    }

    public void setNames(ArrayList<String> names) {
        this.names = names;
    }
}
