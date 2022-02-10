package ru.draimdev.dmlibspigot.GUI.events

import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import ru.draimdev.dmlibspigot.GUI.GUI

class GUIOpenEvent(val player: Player, val gui: GUI) : Event() {
    companion object {
        private val HANDLERS = HandlerList()
        fun getHandlerList(): HandlerList {
            return HANDLERS
        }
    }

    override fun getHandlers(): HandlerList {
        return HANDLERS
    }
}