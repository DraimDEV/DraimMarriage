package ru.draimdev.dmlibspigot.GUI

import org.bukkit.inventory.ItemStack

class GUIItem(val itemStack: ItemStack, val clickRunnable: () -> Unit = null)