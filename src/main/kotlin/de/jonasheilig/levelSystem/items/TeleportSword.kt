package de.jonasheilig.levelSystem.items

import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

object TeleportSword {
    fun create(): ItemStack {
        val item = ItemStack(Material.DIAMOND_SWORD)
        val meta: ItemMeta = item.itemMeta ?: return item
        meta.setDisplayName("${ChatColor.GOLD}Teleport Sword")
        meta.isUnbreakable = true
        item.itemMeta = meta
        return item
    }
}
