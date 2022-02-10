package ru.draimdev.dmlibspigot.ActionBar

import com.cryptomorin.xseries.messages.ActionBar
import org.bukkit.entity.Player
import ru.draimdev.dmlibspigot.DMLIBSpigot

fun sendActionBar(player: Player, actionBar: DMActionBar) {
    ActionBar.sendActionBar(
        DMLIBSpigot.instance,
        player, actionBar.message, actionBar.duration
    )
}

fun broadcastActionBar(actionBar: DMActionBar) {
    ActionBar.sendPlayersActionBar(actionBar.message)
}

fun clearActionBar(player: Player) {
    ActionBar.clearActionBar(player)
}

fun clearAllActionBars() {
    ActionBar.clearPlayersActionBar()
}