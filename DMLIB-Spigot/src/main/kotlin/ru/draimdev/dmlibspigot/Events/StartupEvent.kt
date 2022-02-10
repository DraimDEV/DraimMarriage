package ru.draimdev.dmlibspigot.Events

import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import ru.draimdev.dmlibspigot.DMLIBSpigot

class StartupEvent(val plugin: DMLIBSpigot) : Event() {
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