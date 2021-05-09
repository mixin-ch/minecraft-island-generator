package ch.mixin.islandgenerator.metaData;

import java.util.ArrayList;

public class WorldData {
    private int spawnRadius;
    private ArrayList<IslandData> islandDatas;

    public WorldData(int spawnRadius, ArrayList<IslandData> islandDatas) {
        this.spawnRadius = spawnRadius;
        this.islandDatas = islandDatas;
    }

    public int getSpawnRadius() {
        return spawnRadius;
    }

    public void setSpawnRadius(int spawnRadius) {
        this.spawnRadius = spawnRadius;
    }

    public ArrayList<IslandData> getIslandDatas() {
        return islandDatas;
    }

    public void setIslandDatas(ArrayList<IslandData> islandDatas) {
        this.islandDatas = islandDatas;
    }
}
