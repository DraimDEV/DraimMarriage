package ru.draimdev.dmlibspigot.Config

import com.cryptomorin.xseries.SkullUtils
import com.cryptomorin.xseries.XEnchantment
import com.cryptomorin.xseries.XMaterial
import com.cryptomorin.xseries.XSound
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.EntityType
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import ru.draimdev.dmlibspigot.Item.ItemBuilder
import ru.draimdev.dmlibspigot.Sound.DMSound
import ru.draimdev.dmlibspigot.Sound.SoundBuilder
import ru.draimdev.dmlibspigot.Title.DMTitle
import ru.draimdev.dmlibspigot.Title.TitleBuilder
import ru.draimdev.dmlibspigot.Util.colorString
import ru.draimdev.dmlibspigot.Util.isInt
import java.io.File
import java.io.IOException

class ConfigManager(private val pl: JavaPlugin, private val fileName: String) {
    val file: File
    val config: FileConfiguration

    init {
        val dataFolder = pl.dataFolder
        if (!dataFolder.exists()) dataFolder.mkdir()         // Если не находит папку - он её создаст
        this.file = File(dataFolder, fileName)        // Обьяснение что file может находиться в папке и у него есть имя
        if (!file.exists()) {        // Если не находит файл
            try {
                file.createNewFile()                // Попытка создания файла
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        this.config = YamlConfiguration.loadConfiguration(file)        // Подхват что конфиг - является этим файлом
    }

    fun save() {    // Функция на сохранение файла
        config.save(file)
    }

    private fun pathNotFound(path: String) { // Если не нашло путь - отпишет что ты еблан
        pl.logger.warning("Путь $path не был найден в $fileName")
    }

    private fun isNot(type: String, name: String, path: String) { // Если не является назначеным типом
        pl.logger.warning("$name не является $type в файла $fileName ($path)")
    }


    // Список Type
    fun getString(path: String, def: String = "${ChatColor.RED}$path"): String {
        return if (config.isSet(path)) {
            colorString(config.getString(path, def)!!)
        } else {
            pathNotFound(path)
            def
        }
    }

    fun getSound(path: String, def: Sound = Sound.ENTITY_VILLAGER_NO): Sound {
        if (config.isSet(path)) {
            val soundName = getString(path)
            val xSoundOptional = XSound.matchXSound(soundName)
            if (xSoundOptional.isPresent) {
                return xSoundOptional.get().parseSound()!!
            }
            isNot("Звук", soundName, path)
            return def
        }
        pathNotFound(path)
        return def
    }

    fun getInt(path: String, def: Int = -1): Int {
        if (config.isSet(path)) {
            if (config.isInt(path)) {
                return config.getInt(path, def)
        }
            isNot("Ин-ТЕГ", getString(path), path)
            return def
        }
        pathNotFound(path)
        return def
    }

    fun getDouble(path: String, def: Double = -1.0): Double {
        if(config.isSet(path)) {
            if (config.isDouble(path)) {
                return config.getDouble(path, def)
            }
            isNot("Двойной", getString(path), path)
        }
        pathNotFound(path)
        return def
    }

    fun getLong(path: String, def: Int = -1) = getInt(path, def).toLong()

    fun getFloat(path: String, def: Double = -1.0) = getDouble(path, def).toFloat()

    fun getBoolean(path: String, def: Boolean = false): Boolean {
        if (config.isSet(path)) {
            if (config.isBoolean(path)) {
                return config.getBoolean(path, def)
            }
            isNot("Boolean", getString(path), path)
            return def
        }
        pathNotFound(path)
        return def
    }

    fun getStringList(path: String): List<String> {
        if (config.isSet(path)) {
            if (config.isList(path)) {
                val stringList = config.getStringList(path)
                val coloredStringList: MutableList<String> = ArrayList()
                for (string in stringList) {
                    coloredStringList.add(colorString(string))
                }
                return coloredStringList
            }
            isNot("StringList", "Value", path)
            return ArrayList()
        }
        pathNotFound(path)
        return ArrayList()
    }

    fun getEntityType(path: String): EntityType {
        if (config.isSet(path)) {
            val entityName = getString(path)
            for (entity in EntityType.values()) {
                if (entity.toString().equals(entityName, ignoreCase = true)) {
                    return entity
                }
            }
            isNot("EntityType", entityName, path)
            return EntityType.PIG
        }
        pathNotFound(path)
        return EntityType.PIG
    }

    fun getXMaterial(path: String): XMaterial {
        if (config.isSet(path)) {
            val materialName = getString(path)
            val xMaterialOptional = XMaterial.matchXMaterial(materialName)
            if (xMaterialOptional.isPresent && xMaterialOptional.get().parseMaterial() != null) {
                return xMaterialOptional.get()
            }
            isNot("Material", materialName, path)
            return XMaterial.STONE
        }
        pathNotFound(path)
        return XMaterial.STONE
    }

    fun getMaterial(path: String): Material {
        if (config.isSet(path)) {
            val materialName = getString(path)
            val xMaterialOption = XMaterial.matchXMaterial(materialName)
            if (xMaterialOption.isPresent && xMaterialOption.get().parseMaterial() != null) {
                return xMaterialOption.get().parseItem()!!.type
            }
            isNot("Material", materialName, path)
            return Material.STONE
        }
        pathNotFound(path)
        return Material.STONE
    }

    fun getEnchantments(path: String): HashMap<Enchantment, Int> {
        val enchs = HashMap<Enchantment, Int>()
        val enchantments = getStringList(path)
        for (enchantment in enchantments) {
            val enchNameAndLevel = enchantment.split(":").toTypedArray()
            if (enchNameAndLevel.size == 2) {
                val xEnchantmentOptional = XEnchantment.matchXEnchantment(enchNameAndLevel[0])
                if (xEnchantmentOptional.isPresent) {
                    if (isInt(enchNameAndLevel[1])) {
                        enchs[xEnchantmentOptional.get().parseEnchantment()!!] = enchNameAndLevel[1].toInt()
                    } else {
                        isNot("Integer", enchNameAndLevel[1], path)
                    }
                } else {
                    isNot("Enchantment", enchNameAndLevel[0], path)
                }
            } else {
                isNot("Valid Enchantment Format", enchNameAndLevel.contentToString(), path)
            }
        }
        return enchs
    }

    fun setEnchantments(path: String, enchants: Map<Enchantment, Int>) {
        val enchs: MutableList<String> = ArrayList()
        for ((key, value) in enchants) {
            enchs.add("${key.key}:$value")
        }
        config[path] = enchs
    }

    fun getItemStack(path: String): ItemStack {
        if (config.isSet(path)) {
            val itemStack = config.getItemStack(path)
            if (itemStack == null) {
                isNot("ItemStack", "?", path)
                return ItemStack(Material.STONE)
            }
            return itemStack
        }
        pathNotFound(path)
        return ItemStack(Material.STONE)
    }

    fun getHead(path: String): ItemStack {
        if (config.isSet(path)) {
            val owner = getString("$path.head-owner")
            var name = Component.text("Head")
            var amount = 1
            val lore: MutableList<Component> = ArrayList()
            if (config.isSet("$path.amount")) {
                amount = getInt("$path.amount")
            }
            if (config.isSet("$path.name")) {
                name = Component.text(getString("$path.name"))
            }
            if (config.isSet("$path.lore")) {
                getStringList("$path.lore").forEach { lore.add(Component.text(it)) }
            }
            val head: ItemStack = ItemBuilder(XMaterial.PLAYER_HEAD)
                .withName(name)
                .withAmount(amount)
                .withLore(lore)
                .withOwner(owner)
                .create()
            if (config.isSet("$path.enchantments")) {
                head.addEnchantments(getEnchantments("$path.enchantments"))
            }
            return head
        }
        pathNotFound(path)
        return ItemStack(Material.STONE)
    }

    fun getDamageCause(path: String): EntityDamageEvent.DamageCause {
        if (config.isSet(path)) {
            val causeName = getString(path)
            for (cause in EntityDamageEvent.DamageCause.values()) {
                if (cause.toString().equals(causeName, true)) {
                    return cause
                }
            }
            isNot("DamageCause", causeName, path)
            return EntityDamageEvent.DamageCause.VOID
        }
        pathNotFound(path)
        return EntityDamageEvent.DamageCause.VOID
    }

    fun getDMTitle(path: String): DMTitle {
        if (config.isSet(path)) {
            val title = Component.text(getString("$path.title"))
            val subtitle = Component.text(getString("$path.subtitle"))
            val inTime = getLong("$path.in")
            val stayTime = getLong("$path.stay")
            val outTime = getLong("$path.out")
            return DMTitle(title, subtitle, inTime, stayTime, outTime)
        }
        pathNotFound(path)
        return TitleBuilder(Component.text("Title"), Component.text("Was not found!")).create()
    }

    fun setDMTitle(path: String, title: DMTitle) {
        config["$path.title"] = MiniMessage.get().serialize(title.title)
        config["$path.subtitle"] = MiniMessage.get().serialize(title.subtitle)
        config["$path.in"] = title.inTime
        config["$path.stay"] = title.stayTime
        config["$path.out"] = title.outTime
    }

    fun getDMSound(path: String): DMSound {
        if (config.isSet(path)) {
            val type = getSound("$path.type")
            val volume = getFloat("$path.volume")
            val pitch = getFloat("$path.pitch")
            return DMSound(type, volume, pitch)
        }
        pathNotFound(path)
        return SoundBuilder(XSound.ENTITY_BAT_DEATH).create()
    }

    fun setDMSound(path: String, sound: DMSound) {
        config["$path.type"] = sound.type.toString()
        config["$path.volume"] = sound.volume
        config["$path.pitch"] = sound.pitch
    }
}