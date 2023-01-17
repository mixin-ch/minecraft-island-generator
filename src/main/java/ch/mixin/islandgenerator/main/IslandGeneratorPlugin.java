package ch.mixin.islandgenerator.main;

import ch.mixin.islandgenerator.command.CommandInitializer;
import ch.mixin.islandgenerator.eventListener.EventListener;
import ch.mixin.islandgenerator.islandGeneration.IslandManager;
import ch.mixin.islandgenerator.loot.LootManager;
import ch.mixin.islandgenerator.metaData.MetaData;
import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public final class IslandGeneratorPlugin extends JavaPlugin {
    public static IslandGeneratorPlugin PLUGIN;
    public static String PLUGIN_NAME;
    public static String ROOT_DIRECTORY_PATH;
    public static String METADATA_DIRECTORY_PATH;
    public static String METADATA_FILE_PATH;
    public static File METADATA_FILE;

    public static boolean useHolographicDisplays;

    static {
        String urlPath = IslandGeneratorPlugin.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String decodedPath = null;

        try {
            decodedPath = URLDecoder.decode(urlPath, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        ROOT_DIRECTORY_PATH = decodedPath.substring(0, decodedPath.lastIndexOf("/"));
    }

    private MetaData metaData;
    private IslandManager islandManager;
    private LootManager lootManager;
    private Random random;
    public boolean PluginFlawless;

    @Override
    public void onEnable() {
        PLUGIN = this;
        PLUGIN_NAME = getDescription().getName();
        System.out.println(PLUGIN_NAME + " enabled");
        setup();
        load();
        start();
    }

    @Override
    public void onDisable() {
        metaData.save();
        System.out.println(PLUGIN_NAME + " disabled");
    }

    private void setup() {
        saveDefaultConfig();
        setupMetaData();

        random = new Random();
        CommandInitializer.setupCommands(this);
        islandManager = new IslandManager(this);
        lootManager = new LootManager(this);
        getServer().getPluginManager().registerEvents(new EventListener(this), this);
    }

    public void reload() {
        metaData.save();
        load();
    }

    private void load() {
        super.reloadConfig();
        loadMetaData();
        loadDependentPlugins();
        islandManager.regenerateHolograms();
    }

    private void setupMetaData() {
        METADATA_DIRECTORY_PATH = ROOT_DIRECTORY_PATH + "/" + PLUGIN_NAME;
        final File folder = new File(METADATA_DIRECTORY_PATH);
        if (!folder.exists() && !folder.mkdirs())
            throw new RuntimeException("Failed to create Metadata Directory.");

        METADATA_FILE_PATH = METADATA_DIRECTORY_PATH + "/Metadata.txt";
        METADATA_FILE = new File(METADATA_FILE_PATH);
        if (!METADATA_FILE.exists()) {
            try {
                METADATA_FILE.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        loadMetaData();
    }

    private void loadMetaData() {
        String jsonString = String.join("\n", readFile(METADATA_FILE));

        if (jsonString.equals("")) {
            metaData = new MetaData(new HashMap<>());
        } else {
            metaData = new Gson().fromJson(jsonString, MetaData.class);
        }
    }

    private void start() {
        PluginFlawless = true;
    }

    private void loadDependentPlugins() {
        useHolographicDisplays = Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays");
        System.out.println("HolographicDisplays: " + useHolographicDisplays);
    }

    public static ArrayList<String> readFile(File file) {
        ArrayList<String> text = new ArrayList<>();
        try {
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                text.add(myReader.nextLine());
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return text;
    }

    public static void writeFile(File file, String text) {
        try {
            FileWriter myWriter = new FileWriter(file);
            myWriter.write(text);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MetaData getMetaData() {
        return metaData;
    }

    public IslandManager getIslandManager() {
        return islandManager;
    }

    public LootManager getLootManager() {
        return lootManager;
    }

    public Random getRandom() {
        return random;
    }
}
