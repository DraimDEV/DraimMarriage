package ru.draimdev.dmlibspigot

import org.bukkit.plugin.java.JavaPlugin
import ru.draimdev.dmlibspigot.DataBase.DataSource
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import net.milkbowl.vault.economy.Economy
import ru.draimdev.dmlibspigot.Player.PlayerQuery

class DMLIBSpigot : JavaPlugin() {
    companion object {
        lateinit var instance: DMLIBSpigot
        lateinit var dataSource: DataSource
        lateinit var bukkitAudiences: BukkitAudiences
        var vaultEcon: Economy? = null
        lateinit var playerQuery: PlayerQuery

    }

    override fun onEnable() {
    }
}