package ru.draimdev.draimmarriage.Config;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import ru.draimdev.draimmarriage.DraimMarriage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class MessageConfig {
    private static MessageConfig mc;
    private File f;
    private FileConfiguration fc;
    public void setUp() {
        if(this.f == null) {
            this.f = new File(DraimMarriage.getInstance().getDataFolder(), "config.yml");
        }
        this.fc = YamlConfiguration.loadConfiguration(this.f);
        if (!this.f.exists()) {
            try (final InputStream in = DraimMarriage.getInstance().getResource("config.yml")) {
                Files.copy(in, this.f.toPath());
                this.fc = YamlConfiguration.loadConfiguration(this.f);
                Bukkit.getServer().getConsoleSender().sendMessage("[DraimMarriage] Файл локализации успешно создан.");
            }
            catch (IOException e) {
                Bukkit.getServer().getConsoleSender().sendMessage("[DraimMarriage]");
            }
        }
    }

    public void reloadCFG() {
        this.f = new File(DraimMarriage.getInstance().getDataFolder(), "config.yml");
        this.fc = YamlConfiguration.loadConfiguration(this.f);
    }

    public FileConfiguration getCFG() {
        if (this.fc == null) {
            this.reloadCFG();
        }
        return this.fc;
    }
}
