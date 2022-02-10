package ru.draimdev.dmlibspigot.Player

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable
import ru.draimdev.dmlibspigot.Config.configuration.Settings
import ru.draimdev.dmlibspigot.Config.configuration.SoundType
import ru.draimdev.dmlibspigot.Config.configuration.TitleType
import ru.draimdev.dmlibspigot.DMLIBSpigot
import ru.draimdev.dmlibspigot.Title.TitleBuilder
import java.util.*

private val QUERY = DMLIBSpigot.playerQuery

fun isPlayer(input: String): Boolean {
    val player = Bukkit.getPlayer(input)
    return player != null && player.isOnline
}

fun isPlayer(input: UUID): Boolean {
    val player = Bukkit.getPlayer(input)
    return player != null && player.isOnline
}

fun getName(offlinePlayer: OfflinePlayer): String {
    return if (offlinePlayer.isOnline) {
        offlinePlayer.player!!.name
    } else getName(offlinePlayer.uniqueId)
}

fun getName(uuid: UUID) = QUERY.playerNames.getOrElse(uuid) { "?" }

fun teleport(player: Player, target: Player) = teleport(player, target.location)

fun teleport(player: Player, location: Location) {
    if (player.hasPermission("dmlib.teleport.bypass")) {
        player.teleport(location)
        SoundType.TELEPORT_TP.get().play(player)
        TitleType.TELEPORT_TELEPORT.get().send(player)
        return
    }
    object : BukkitRunnable() {
        var countdown = Settings.TELEPORT_DELAY
        val pLoc = player.location
        val x = pLoc.x
        val z = pLoc.z
        override fun run() {
            val currentLoc = player.location
            val currentX = currentLoc.x
            val currentZ = currentLoc.z
            if (currentX != x || currentZ != z) {
                TitleBuilder(TitleType.TELEPORT_FAIL.get())
                    .replaceTitle("{time}", countdown.toString() + "")
                    .replaceSubtitle("{time}", countdown.toString() + "")
                    .create().send(player)
                SoundType.TELEPORT_FAIL.get().play(player)
                cancel()
                return
            }
            if (countdown <= 0) {
                TitleBuilder(TitleType.TELEPORT_TELEPORT.get())
                    .replaceTitle("{time}", countdown.toString() + "")
                    .replaceSubtitle("{time}", countdown.toString() + "")
                    .create().send(player)
                SoundType.TELEPORT_TP.get().play(player)
                Bukkit.getScheduler().runTask(DMLIBSpigot.instance, Runnable { player.teleport(location) })
                cancel()
                return
            }
            // Ticking (Waiting to be teleported)
            TitleBuilder(TitleType.TELEPORT_TICKING.get())
                .replaceTitle("{time}", countdown.toString() + "")
                .replaceSubtitle("{time}", countdown.toString() + "")
                .create().send(player)
            SoundType.TELEPORT_TICK.get().play(player)
            countdown--
        }
    }.runTaskTimerAsynchronously(DMLIBSpigot.instance, 0, 20)
}

fun give(target: Player, item: ItemStack) {
    if (target.inventory.firstEmpty() == -1) {
        target.world.dropItem(target.location, item)
    } else {
        target.inventory.addItem(item)
    }
}