package ru.draimdev.dmlibspigot.Config

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
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


}