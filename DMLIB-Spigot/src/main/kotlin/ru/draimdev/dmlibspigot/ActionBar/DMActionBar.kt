package ru.draimdev.dmlibspigot.ActionBar

import org.bukkit.entity.Player

class DMActionBar(val message: String, val duration: Long) {

    fun send(player: Player) {
        sendActionBar(player, this)
    }

    fun broadcast() {
        broadcastActionBar(this)
    }
}