package ru.draimdev.dmlibspigot.GUI

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.plugin.java.JavaPlugin

class GUIListeners(val pl: JavaPlugin, val mngr: GUIManager) : Listener {

    @EventHandler
    fun onClose(event: InventoryCloseEvent) {
        if (event.player is Player) {
            val player = event.player as Player
            if (mngr.guis.containsKey(player.uniqueId)) {
                if (mngr.guis[player.uniqueId]!!.onClose != null) {
                    Bukkit.getScheduler().runTaskLater(pl, Runnable {
                        mngr.open(player, mngr.guis[player.uniqueId]!!.onClose!!)
                    }, 1)
                } else {
                    mngr.guis.remove(player.uniqueId)
                }
            }
        }
    }

    @EventHandler
    fun onClick(event: InventoryClickEvent){
        if (event.whoClicked is Player) {
            val player = event.whoClicked as Player
            if (mngr.guis.containsKey(player.uniqueId)) {
                event.isCancelled = true
                player.updateInventory()
                mngr.guis[player.uniqueId]!!.items[event.rawSlot]?.run(event)
            }
        }
    }
}