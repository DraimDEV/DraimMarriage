package ru.draimdev.dmlibspigot.Config.configuration

import net.kyori.adventure.text.Component
import org.bukkit.configuration.file.FileConfiguration
import ru.draimdev.dmlibspigot.Config.ConfigManager
import ru.draimdev.dmlibspigot.Title.DMTitle
import ru.draimdev.dmlibspigot.Title.TitleBuilder
import java.util.*

enum class TitleType(val defaultValue: DMTitle) {
    TELEPORT_TICKING(
        TitleBuilder(
        Component.text("&b&lТелепортация"),
        Component.text("&7{time}&8сек."),
        20, 40, 20).create()),
    TELEPORT_FAIL(TitleBuilder(
        Component.text("&b&lТелепортация"),
        Component.text("&cТелепортация отменена.."),
        20, 60, 20).create()),
    TELEPORT_TELEPORT(TitleBuilder(
        Component.text("&b&lТелепортация"),
        Component.text("&7Телепорт..."),
        20, 60, 20).create());

    companion object {
        private val titleType = EnumMap<TitleType, DMTitle>(TitleType::class.java)

        fun load(config: ConfigManager) {
            val fileConfig: FileConfiguration = config.config
            titleType.clear()
            values().forEach {
                if (!fileConfig.isSet(it.toString())){
                    config.setDMTitle(it.toString(), it.defaultValue)
                } else {
                    titleType[it] = config.getDMTitle(it.toString())
                }
            }
            config.save()
        }
    }

    fun get(): DMTitle {
        return titleType.getOrDefault(this, this.defaultValue)
    }
}