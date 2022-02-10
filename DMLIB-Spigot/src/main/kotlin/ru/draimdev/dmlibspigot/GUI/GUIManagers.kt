package ru.draimdev.dmlibspigot.GUI

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import ru.draimdev.dmlibspigot.GUI.events.GUIOpenEvent
import ru.draimdev.dmlibspigot.Player.isPlayer
import java.util.*

class GUIManager {
    val guis = HashMap<UUID, GUI>()

    fun open(player: Player, gui: GUI) {
        player.openInventory(gui.inventory)
        guis[player.uniqueId] = gui
        Bukkit.getPluginManager().callEvent(GUIOpenEvent(player, gui))
    }

    fun closeAll() {
        guis.forEach { (playerId) ->
            run {
                if (isPlayer(playerId)) {
                    Bukkit.getPlayer(playerId)?.closeInventory()
                }
                guis.remove(playerId)
            }
        }
    }
}