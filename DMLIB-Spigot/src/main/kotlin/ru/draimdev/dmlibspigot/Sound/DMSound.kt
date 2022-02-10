package ru.draimdev.dmlibspigot.Sound

import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.entity.Player

class DMSound(val type: Sound, val volume: Float, val pitch: Float) {

    fun play(player: Player) = player.playSound(player.location, type, volume, pitch)

    fun broadcast() = Bukkit.getOnlinePlayers().forEach { play(it) }
}