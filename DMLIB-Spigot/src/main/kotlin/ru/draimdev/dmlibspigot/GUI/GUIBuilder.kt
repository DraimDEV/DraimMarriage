package ru.draimdev.dmlibspigot.GUI

import org.bukkit.Bukkit
import org.bukkit.inventory.ItemStack
import ru.draimdev.dmlibspigot.Util.colorString

class GUIBuilder(var title: String, var rows: Int, var items: HashMap<Int, GUIItem>, var onClose: GUI?) {

    constructor() : this("", 1, HashMap(), null)

    constructor(title: String) : this(title, 1, HashMap(), null)

    constructor(title: String, rows: Int) : this(title, rows, HashMap(), null)

    fun withTitle(title: String) : GUIBuilder {
        this.title = title
        return this
    }

    fun withRows(rows: Int) : GUIBuilder {
        this.rows = rows
        return this
    }

    fun setItem(slot: Int, item: GUIItem) : GUIBuilder {
        this.items[slot] = item
        return this
    }

    fun setItem(slot: Int, item: ItemStack) : GUIBuilder {
        this.items[slot] = GUIItem(item)
        return this
    }

    fun openOnClose(gui: GUI) : GUIBuilder {
        this.onClose = gui
        return this
    }

    fun create() : GUI {
        val inventory =  Bukkit.createInventory(null, this.rows*9, colorString(title))
        val items = HashMap<Int, ClickRunnable>()
        this.items.forEach { (slot, item) ->
            inventory.setItem(slot, item.itemStack)
            if (item.clickRunnable != null) {
                items[slot] = item.clickRunnable
            }
        }
        return GUI(inventory, items, onClose)
    }
}