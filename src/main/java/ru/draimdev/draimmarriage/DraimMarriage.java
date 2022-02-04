package ru.draimdev.draimmarriage;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import ru.draimdev.draimmarriage.Commands.MainCommands;
import ru.draimdev.draimmarriage.Commands.TabComplete;
import ru.draimdev.draimmarriage.Config.MessageConfig;

import java.util.List;

public final class DraimMarriage extends JavaPlugin
{
    public static FileConfiguration config;
    private static DraimMarriage instance;

    // Подхват всех настроек из config.yml
    public static String getConfigString(String path) {
        return config.getString(path);
    }
    public static List<String> getConfigStringList(String path) {
        return config.getStringList(path);
    }

    public static DraimMarriage getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();
        config = getConfig();
        MessageConfig.getMSG().setUp();
    }

    @Override
    public void onDisable() {
        this.getLogger().info("Плагин успешно отключен.");

    }

    public void initCMDs() {
        getCommand("draimmarriage").setExecutor(new MainCommands(this));
        getCommand("draimmarriage").setTabCompleter(new TabComplete());
    }
}
