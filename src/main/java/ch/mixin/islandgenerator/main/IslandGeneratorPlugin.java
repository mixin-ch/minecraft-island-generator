package ch.mixin.islandgenerator.main;

import ch.mixin.islandgenerator.eventListener.EventListener;
import ch.mixin.islandgenerator.islandGeneration.IslandManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ch.mixin.islandgenerator.command.CommandInitializer;
import ch.mixin.islandgenerator.metaData.MetaData;
import ch.mixin.main.MixedCatastrophesPlugin;
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

    public static MixedCatastrophesPlugin MixedCatastrophesPlugin = (MixedCatastrophesPlugin) Bukkit.getServer().getPluginManager().getPlugin("MixedCatastrophes");
    public static boolean useMixedCatastrophesPlugin;

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

    protected MetaData metaData;
    protected IslandManager islandManager;
    protected Random random;

    @Override
    public void onEnable() {
        PLUGIN = this;
        PLUGIN_NAME = getDescription().getName();
        System.out.println(PLUGIN_NAME + " enabled");
        loadConfig();
        load();
        initialize();
        start();
    }

    @Override
    public void onDisable() {
        System.out.println(PLUGIN_NAME + " disabled");
    }

    public void reload() {
        super.reloadConfig();
        load();
    }

    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    private void initialize() {
        random = new Random();
        CommandInitializer.setupCommands(this);
        islandManager = new IslandManager(this);
        getServer().getPluginManager().registerEvents(new EventListener(this), this);
    }

    private void load() {
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

        String jsonString = String.join("\n", readFile(METADATA_FILE));
        if (jsonString.equals("")) {
            metaData = new MetaData(new HashMap<>());
        } else {
            Gson gson = new GsonBuilder()
                    .enableComplexMapKeySerialization()
                    .create();
            metaData = gson.fromJson(jsonString, MetaData.class);
        }

        useMixedCatastrophesPlugin = MixedCatastrophesPlugin != null;
        System.out.println("MixedCatastrophesPlugin: " + useMixedCatastrophesPlugin);
        useHolographicDisplays = Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays");
        System.out.println("HolographicDisplays: " + useHolographicDisplays);
    }

    private void start() {
        islandManager.regenerateHolograms();
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

    public Random getRandom() {
        return random;
    }
}
