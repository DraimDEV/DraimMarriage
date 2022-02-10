package ru.draimdev.dmlibspigot.Util

import org.bukkit.ChatColor

fun colorString(string: String): String { // Замена элемента
        return ChatColor.translateAlternateColorCodes('&', string)
}
