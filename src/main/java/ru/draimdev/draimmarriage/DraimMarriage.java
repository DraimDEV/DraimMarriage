package ru.draimdev.draimmarriage;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import ru.draimdev.draimmarriage.Commands.MainCommands;
import ru.draimdev.draimmarriage.Commands.TabComplete;

import java.util.List;

public final class DraimMarriage extends JavaPlugin
{
    public static FileConfiguration config;
    private static DraimMarriage instance;

    public static List<String> getConfigStringList(String path) {
        return config.getStringList(path);
    }

    public static DraimMarriage getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        config = getConfig();
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
