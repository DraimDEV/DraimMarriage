package ru.draimdev.dmlibspigot.Util

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import ru.draimdev.dmlibspigot.DMLIBSpigot

private val BUKKIT_AUDIENCES = DMLIBSpigot.bukkitAudiences

fun sendMessage(player: OfflinePlayer, message: String) {
    if (player.isOnline) {
        sendMessage(player.player!!, message)
    }
}

fun sendMessageClean(player: OfflinePlayer, message: String) {
    if (player.isOnline) {
        sendMessageClean(player.player!!, message)
    }
}

fun sendMessage(player: Player, message: String) = BUKKIT_AUDIENCES.player(player).sendMessage(Component.text(setPlaceholders(player, message)))

fun sendMessageClean(player: Player, message: String) = player.sendMessage(setPlaceholders(player, message))

fun sendMessage(target: CommandSender, message: String){
    if (target is Player){
        sendMessage(player = target, message)
    } else {
        BUKKIT_AUDIENCES.sender(target).sendMessage(Component.text(message))
    }
}

fun sendMessageClean(target: CommandSender, message: String) {
    if (target is Player) {
        sendMessageClean(player = target, message)
    } else {
        target.sendMessage(message)
    }
}

fun broadcastMessage(message: String, perm: String) {
    sendMessage(Bukkit.getConsoleSender(), message)
    Bukkit.getOnlinePlayers().forEach {
        if (it.hasPermission(perm) || perm == "") {
            sendMessage(it, message)
        }
    }
}

fun broadcastMessages(messages: List<String>, perm: String) = messages.forEach { broadcastMessage(it, perm) }

fun broadcastMessageClean(message: String, perm: String) {
    sendMessageClean(Bukkit.getConsoleSender(), message)
    Bukkit.getOnlinePlayers().forEach {
        if (it.hasPermission(perm) || perm == "") {
            sendMessageClean(it, message)
        }
    }
}

fun broadcastMessagesClean(messages: List<String>, perm: String) = messages.forEach { broadcastMessageClean(it, perm) }
