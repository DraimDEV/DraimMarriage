package ru.draimdev.dmlibspigot

import org.bukkit.plugin.java.JavaPlugin
import ru.draimdev.dmlibspigot.DataBase.DataSource
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit
import ru.draimdev.dmlibspigot.Config.ConfigManager
import ru.draimdev.dmlibspigot.Config.configuration.Lang
import ru.draimdev.dmlibspigot.Config.configuration.Settings
import ru.draimdev.dmlibspigot.Config.configuration.SoundType
import ru.draimdev.dmlibspigot.Config.configuration.TitleType
import ru.draimdev.dmlibspigot.Events.StartupEvent
import ru.draimdev.dmlibspigot.GUI.GUIListeners
import ru.draimdev.dmlibspigot.GUI.GUIManagers
import ru.draimdev.dmlibspigot.Player.PlayerListeners
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
        setupListeners()
        if (Settings.VAULT) {
            setupVault()
        }
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

    private fun setupListeners() {
        val pluginManager = Bukkit.getPluginManager()
        pluginManager.registerEvents(GUIListeners(this, guiManager), this)
        pluginManager.registerEvents(PlayerListeners(this, playerQuery), this)
    }

    private fun setupVault() {
        if (!setupVaultEconomy()) {
            logger.severe("Не найден Vault! Сервер отключеается.")
            server.pluginManager.disablePlugin(this)
        }
    }

    private fun setupVaultEconomy() : Boolean {
        if (server.pluginManager.getPlugin("Vault") != null) {
            val rsp = server.servicesManager.getRegistration(Economy::class.java)
            if (rsp != null) {
                vaultEcon = rsp.provider
                return true
            }
        }
        return false
    }
}