package ru.draimdev.dmlibspigot.Config.configuration

import com.cryptomorin.xseries.XSound
import org.bukkit.configuration.file.FileConfiguration
import ru.draimdev.dmlibspigot.Config.ConfigManager
import ru.draimdev.dmlibspigot.Sound.DMSound
import ru.draimdev.dmlibspigot.Sound.SoundBuilder
import java.util.*

enum class SoundType(val defaultValue: DMSound) {
    COMMAND_FAIL(SoundBuilder(XSound.ENTITY_VILLAGER_NO, 10f, 1f).create()),
    TELEPORT_TICK(SoundBuilder(XSound.UI_BUTTON_CLICK, 10f, 1f).create()),
    TELEPORT_FAIL(SoundBuilder(XSound.ENTITY_VILLAGER_HURT, 10f, 1f).create()),
    TELEPORT_TP(SoundBuilder(XSound.ENTITY_CAT_PURR, 10f, 1f).create());

    companion object {
        private val soundType = EnumMap<SoundType, DMSound>(SoundType::class.java)

        fun load(config: ConfigManager) {
            val fileConfig: FileConfiguration = config.config
            soundType.clear()
            values().forEach {
                if (!fileConfig.isSet(it.toString())){
                    config.setDMSound(it.toString(), it.defaultValue)
                } else {
                    soundType[it] = config.getDMSound(it.toString())
                }
            }
            config.save()
        }
    }

    fun get(): DMSound {
        return soundType.getOrDefault(this, this.defaultValue)
    }
}