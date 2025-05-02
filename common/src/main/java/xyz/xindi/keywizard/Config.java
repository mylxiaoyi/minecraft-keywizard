package xyz.xindi.keywizard;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.architectury.platform.Platform;
import net.minecraft.world.item.Item;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Config {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String CONFIG_FILENAME = "keywizard.json";
    private static ConfigData configData;

    public static boolean logDirtBlock = true;
    public static int magicNumber = 42;
    public static String magicNumberIntroduction = "The magic number is... ";
    public static Set<Item> items = new HashSet<>();

    public static void init() {
        loadConfig();
    }

    private static void loadConfig() {
        File configFile = getConfigFile();

        if (configFile.exists()) {
            try (FileReader reader = new FileReader(configFile)) {
                configData = GSON.fromJson(reader, ConfigData.class);
                applyConfig();
            } catch (IOException e) {
                KeyWizardCommon.LOGGER.error("Failed to load config", e);
                createDefaultConfig();
            }
        } else {
            createDefaultConfig();
        }
    }

    private static void createDefaultConfig() {
        configData = new ConfigData();
        configData.logDirtBlock = true;
        configData.magicNumber = 42;
        configData.magicNumberIntroduction = "The magic number is... ";
        configData.items = List.of("minecraft:iron_ingot");

        saveConfig();
        applyConfig();
    }

    private static void saveConfig() {
        File configFile = getConfigFile();
        try (FileWriter writer = new FileWriter(configFile)) {
            GSON.toJson(configData, writer);
        } catch (IOException e) {
            KeyWizardCommon.LOGGER.error("Failed to save config", e);
        }
    }

    private static void applyConfig() {
        logDirtBlock = configData.logDirtBlock;
        magicNumber = configData.magicNumber;
        magicNumberIntroduction = configData.magicNumberIntroduction;
    }

    private static File getConfigFile() {
        Path configDir = Platform.getConfigFolder();
        return new File(configDir.toFile(), CONFIG_FILENAME);
    }

    private static class ConfigData {
        boolean logDirtBlock;
        int magicNumber;
        String magicNumberIntroduction;
        List<String> items = new ArrayList<>();
    }
}