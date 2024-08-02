package de.jonasheilig.levelSystem.items

import de.jonasheilig.levelSystem.LevelSystem
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType

object DayStick {

    fun create(plugin: LevelSystem): ItemStack {
        val item = ItemStack(Material.STICK)
        val meta: ItemMeta = item.itemMeta ?: return item
        meta.setDisplayName("${ChatColor.YELLOW}${ChatColor.BOLD}Day Stick")
        meta.lore = listOf("${ChatColor.BLUE}Use this stick to make it day", "${ChatColor.RED}Cooldown: 1 minute")

        val key = NamespacedKey(plugin, "day_stick")
        meta.persistentDataContainer.set(key, PersistentDataType.BYTE, 1.toByte())

        item.itemMeta = meta
        return item
    }
}
