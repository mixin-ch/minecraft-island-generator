package ch.mixin.islandgenerator.metaData;

import ch.mixin.islandgenerator.main.IslandGeneratorPlugin;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MetaData {
    private HashMap<String, WorldData> worldDataMap;

    public MetaData(HashMap<String, WorldData> worldDataMap) {
        this.worldDataMap = worldDataMap;
        save();
    }

    private void initializeWorldsInConfig() {
        List<String> worldNames = IslandGeneratorPlugin.PLUGIN.getConfig().getStringList("worlds");
        for (String worldName : worldNames) {
            if (!worldDataMap.containsKey(worldName))
                worldDataMap.put(worldName, new WorldData(0, new ArrayList<>()));
        }
    }

    public void save() {
        Gson gson = new GsonBuilder()
                .enableComplexMapKeySerialization()
                .create();
        IslandGeneratorPlugin.writeFile(IslandGeneratorPlugin.METADATA_FILE, gson.toJson(this));
    }

    public HashMap<String, WorldData> getWorldDataMap() {
        return worldDataMap;
    }

    public void setWorldDataMap(HashMap<String, WorldData> worldDataMap) {
        this.worldDataMap = worldDataMap;
    }
}
