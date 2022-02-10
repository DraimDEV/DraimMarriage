package ru.draimdev.dmlibspigot.Title

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import ru.draimdev.dmlibspigot.DMLIBSpigot

private val BUKKIT_AUDIENCES = DMLIBSpigot.bukkitAudiences
fun sendTitle(player: Player, title: DMTitle) = BUKKIT_AUDIENCES.player(player).showTitle(title.toAdventureTitle())
fun broadcastTitle(title: DMTitle) = Bukkit.getOnlinePlayers().forEach { sendTitle(it, title) }
fun clearTitle(player: Player) {
    BUKKIT_AUDIENCES.player(player).clearTitle()
}