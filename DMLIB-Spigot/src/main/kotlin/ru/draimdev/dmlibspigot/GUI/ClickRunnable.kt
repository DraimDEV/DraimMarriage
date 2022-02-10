package ru.draimdev.dmlibspigot.GUI

import org.bukkit.event.inventory.InventoryClickEvent

interface ClickRunnable {
    fun run(clickEvent: InventoryClickEvent)
}