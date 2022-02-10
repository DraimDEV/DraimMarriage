package ru.draimdev.dmlibspigot.GUI

import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import ru.draimdev.dmlibspigot.DMLIBSpigot

class GUI(val inventory: Inventory, val items: HashMap<Int, ClickRunnable>, val onClose: GUI? = null) {

    fun open(player: Player) = DMLIBSpigot.guiManager.open(player, this)
}