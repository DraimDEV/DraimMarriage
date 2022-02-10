package ru.draimdev.dmlibspigot.Config.configuration

import org.bukkit.configuration.file.FileConfiguration
import ru.draimdev.dmlibspigot.Config.ConfigManager
import ru.draimdev.dmlibspigot.Util.colorString
import java.util.*

enum class Lang(val defaultValue: String) {
    TIME_FORMAT_DAYS("%d д, %02d ч. %02d м. %02d с."),
    TIME_FORMAT_HOURS("%02d ч. %02d м. %02d с."),
    TIME_FORMAT_MINUTES("00 ч. %02d м. %02d с."),

    companion object {
        private val lang = EnumMap<Lang, String>(Lang::class.java)

        fun load(config:ConfigManager) {
            val fileConfig: FileConfiguration = config.config
            lang.clear()
            values().forEach {
                if (!fileConfig.isSet(it.toString())) {
                    fileConfig[it.toString()] = it.defaultValue
                } else {
                    lang[it] = config.getString(it.toString())
                }
            }
            config.save()
        }
    }

    fun get(): String {
        return lang.getOrDefault(this, colorString(this.defaultValue))
    }
}