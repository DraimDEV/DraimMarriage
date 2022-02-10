package ru.draimdev.dmlibspigot.Util

import me.clip.placeholderapi.PlaceholderAPI
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import ru.draimdev.dmlibspigot.Config.configuration.Settings

fun setPlaceholders(player: Player, input: String): String {
    if (Settings.PLACEHOLDERS) {
        return PlaceholderAPI.setPlaceholders(player, input)
    }
    if (Settings.DISABLED_MESSAGES) {
        Bukkit.getConsoleSender()
            .sendMessage(ChatColor.RED.toString() + "DMLib: Попытка про парсить PAPI плейсхолдеры, кажется они отключены в конфиге.")
    }
    return input
}