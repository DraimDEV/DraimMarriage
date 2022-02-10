package ru.draimdev.dmlibspigot.Util

import org.bukkit.Material

fun isMaterial(value: String): Boolean {
    for (material in Material.values()) {
        if (material.toString().equals(value, true)) {
            return true
        }
    }
    return false
}