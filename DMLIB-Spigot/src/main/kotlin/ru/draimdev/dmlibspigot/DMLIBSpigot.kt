package ru.draimdev.dmlibspigot

import org.bukkit.plugin.java.JavaPlugin
import ru.draimdev.dmlibspigot.DataBase.DataSource
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import net.milkbowl.vault.economy.Economy
import ru.draimdev.dmlibspigot.Config.ConfigManager
import ru.draimdev.dmlibspigot.Config.configuration.Lang
import ru.draimdev.dmlibspigot.Config.configuration.Settings
import ru.draimdev.dmlibspigot.Config.configuration.SoundType
import ru.draimdev.dmlibspigot.Config.configuration.TitleType
import ru.draimdev.dmlibspigot.Events.StartupEvent
import ru.draimdev.dmlibspigot.GUI.GUIManagers
import ru.draimdev.dmlibspigot.Player.PlayerQuery

class DMLIBSpigot : JavaPlugin() {
    companion object {
        lateinit var instance: DMLIBSpigot
        lateinit var dataSource: DataSource
        lateinit var bukkitAudiences: BukkitAudiences
        var vaultEcon: Economy? = null
        lateinit var playerQuery: PlayerQuery
        lateinit var guiManager: GUIManagers
        lateinit var configManager: ConfigManager

        fun getDataSource(config: ConfigManager): DataSource {
            return if (config.getBoolean("mysql.enabled")) {
                DataSource(config)
            } else dataSource
        }

    }

    override fun onEnable() {
        instance = this
        server.pluginManager.callEvent(StartupEvent(this))
        setupAdventure()
        setupConfig()
        setupDatabase()
        setupManagers()
    }

    private fun setupAdventure() {
        bukkitAudiences = BukkitAudiences.create(this)
    }

    private fun setupConfig(){
        saveDefaultConfig()
        configManager = ConfigManager(this, "config.yml")
        Settings.load(configManager)
        Lang.load(ConfigManager(this, "lang.yml"))
        SoundType.load(ConfigManager(this, "sounds.yml"))
        TitleType.load(ConfigManager(this, "titles.yml"))
    }

    private fun setupDatabase() {
        dataSource = DataSource(configManager)
        dataSource.initPool("DMLIB-Spigot-Pool")
        playerQuery = PlayerQuery(dataSource, this)
    }

    private fun setupManagers() {
        guiManager = GUIManagers()
    }
}