package ru.draimdev.dmlibspigot.Item

import com.cryptomorin.xseries.SkullUtils
import com.cryptomorin.xseries.XMaterial
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextReplacementConfig
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

class ItemBuilder(itemStack: ItemStack) {
    val itemStack = ItemStack(itemStack)
    var name = itemStack.itemMeta.displayName() ?: Component.text("")
    var amount = itemStack.amount
    var lore = itemStack.itemMeta.lore() ?: ArrayList()

    constructor() : this(ItemStack(Material.STONE))

    constructor(material: Material) : this(ItemStack(material))

    constructor(xMaterial: XMaterial) : this(xMaterial.parseItem()!!)

    fun withName(name: Component): ItemBuilder {
        this.name = name
        return this
    }
    @Deprecated("Use Adventure API",
        ReplaceWith("withName(Component.text(name))", "net.kyori.adventure.text.Component"))
    fun withName(name: String) = withName(Component.text(name))

    fun replaceName(target: String, replacement: String): ItemBuilder {
        name = name.replaceText(TextReplacementConfig.builder().matchLiteral(target).replacement(replacement).build())
        return this
    }

    fun withLore(lore: MutableList<Component>): ItemBuilder {
        this.lore = lore
        return this
    }
    @JvmName("withStringLore")
    @Deprecated("Use Adventure API's Component instead of String.")
    fun withLore(lore: MutableList<String>): ItemBuilder {
        var componentLore: MutableList<Component> = ArrayList()
        lore.forEach { componentLore.add(Component.text(it)) }
        this.lore = componentLore
        return this
    }

    fun withLore(vararg lore: Component): ItemBuilder {
        this.lore = mutableListOf(*lore)
        return this
    }
    @Deprecated("User Adventure API's Component instead of String.",
        ReplaceWith("withLore(Component.text(*lore))", "net.kyori.adventure.text.Component"))
    fun withLore(vararg lore: String) = withLore(mutableListOf(*lore))

    fun replaceLore(target: String, replacement: String): ItemBuilder {
        val lore: MutableList<Component> = ArrayList()
        this.lore.forEach { lore.add(it.replaceText(TextReplacementConfig.builder().matchLiteral(target).replacement(replacement).build())) }
        this.lore = lore
        return this
    }

    fun withAmount(amount: Int): ItemBuilder {
        this.amount = amount
        return this
    }

    fun withOwner(owner: String): ItemBuilder {
        if (itemStack.type != XMaterial.PLAYER_HEAD.parseMaterial()) {
            itemStack.type = XMaterial.PLAYER_HEAD.parseMaterial()!!
        }
        var itemMeta = itemStack.itemMeta
        itemMeta = SkullUtils.applySkin(itemMeta, owner)
        itemStack.itemMeta = itemMeta
        return this
    }

    fun setGlowing(): ItemBuilder {
        val itemMeta = itemStack.itemMeta
        itemMeta.addEnchant(Enchantment.DURABILITY, 1, true)
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
        itemStack.itemMeta = itemMeta
        return this
    }

    fun withEnchant(enchant: Enchantment, level: Int): ItemBuilder {
        val itemMeta = itemStack.itemMeta
        itemMeta.addEnchant(enchant, level, true)
        itemStack.itemMeta = itemMeta
        return this
    }

    fun create() : ItemStack {
        val itemStack = ItemStack(this.itemStack)
        itemStack.amount = amount
        val itemMeta = itemStack.itemMeta
        itemMeta.displayName(this.name)
        if (lore.isNotEmpty()) itemMeta.lore(this.lore)
        itemStack.itemMeta = itemMeta
        return itemStack
    }
}