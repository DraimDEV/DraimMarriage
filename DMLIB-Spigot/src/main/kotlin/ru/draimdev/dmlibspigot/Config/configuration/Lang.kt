package ru.draimdev.dmlibspigot.Config.configuration

import org.bukkit.configuration.file.FileConfiguration
import ru.draimdev.dmlibspigot.Config.ConfigManager
import ru.draimdev.dmlibspigot.Util.colorString
import java.util.*

enum class Lang(val defaultValue: String) {
    NO_PERM("&c&l! &8» &fУ вас нет прав, на эьл действие."),
    NO_NUMBER("&c&l! &8» &b{arg} &fне является цифрой."),
    NO_MATERIAL("&c&l! &8» &b{material} &fне является матерьялом"),
    NO_BALANCE("&c&l! &8» &fУ вас не хватает баланса! &7(\${amount})"),
    OFFLINE("&c&l! &8» &e{name} &fне в сети"),
    TIME_FORMAT_DAYS("%d д, %02d ч. %02d м. %02d с."),
    TIME_FORMAT_HOURS("%02d ч. %02d м. %02d с."),
    TIME_FORMAT_MINUTES("00 ч. %02d м. %02d с."),
    COMMAND_RESPONSE_PLAYER("&c&l! &8» &fВы не можете пользоваться этой командой."),
    COMMAND_RESPONSE_CONSOLE("&c&l! &8» &fЭту команду можно использовать только в игре.");

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