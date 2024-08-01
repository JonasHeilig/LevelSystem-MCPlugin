package de.jonasheilig.levelSystem.items

import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import de.jonasheilig.levelSystem.LevelSystem

object TeleportSword {
    fun create(plugin: LevelSystem): ItemStack {
        val item = ItemStack(Material.DIAMOND_SWORD)
        val meta: ItemMeta = item.itemMeta ?: return item

        meta.setDisplayName("${ChatColor.GOLD}Teleport Sword")

        meta.isUnbreakable = true
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)

        val key = NamespacedKey(plugin, "teleport_sword")
        meta.persistentDataContainer.set(key, org.bukkit.persistence.PersistentDataType.BYTE, 1.toByte())

        item.itemMeta = meta
        return item
    }
}
