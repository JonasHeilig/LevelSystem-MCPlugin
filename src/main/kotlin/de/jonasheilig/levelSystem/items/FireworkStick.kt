package de.jonasheilig.levelSystem.items

import de.jonasheilig.levelSystem.LevelSystem
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType
import org.bukkit.NamespacedKey

class FireworkStick(private val plugin: LevelSystem) {
    fun create(): ItemStack {
        val item = ItemStack(Material.BLAZE_ROD)
        val meta = item.itemMeta ?: return item

        val key = NamespacedKey(plugin, "firework_stick")
        meta.persistentDataContainer.set(key, PersistentDataType.BYTE, 1.toByte())

        meta.setDisplayName("${ChatColor.MAGIC}-${ChatColor.LIGHT_PURPLE}Firework Stick${ChatColor.MAGIC}-")
        meta.lore = listOf("${ChatColor.GOLD}Right-click to launch a random firework.")

        item.itemMeta = meta
        return item
    }
}
