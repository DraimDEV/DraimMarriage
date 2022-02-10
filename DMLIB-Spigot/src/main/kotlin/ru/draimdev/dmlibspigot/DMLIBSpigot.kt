package ru.draimdev.dmlibspigot

import org.bukkit.plugin.java.JavaPlugin
import ru.draimdev.dmlibspigot.DataBase.DataSource
import net.kyori.adventure.platform.bukkit.BukkitAudiences

class DMLIBSpigot : JavaPlugin() {
    companion object {
        lateinit var instance: DMLIBSpigot
        lateinit var dataSource: DataSource
        lateinit var bukkitAudiences: BukkitAudiences
    }

    override fun onEnable() {
    }
}