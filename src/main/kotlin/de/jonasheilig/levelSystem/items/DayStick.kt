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
        val cooldownMinutes = plugin.config.getInt("daystick.cooldown_minutes", 1)
        meta.setDisplayName("${ChatColor.MAGIC}-${ChatColor.YELLOW}Day Stick${ChatColor.MAGIC}-")
        meta.lore = listOf("${ChatColor.BLUE}Use this stick to make it day", "${ChatColor.RED}Cooldown: $cooldownMinutes minute(s)")

        val key = NamespacedKey(plugin, "day_stick")
        meta.persistentDataContainer.set(key, PersistentDataType.BYTE, 1.toByte())

        item.itemMeta = meta
        return item
    }
}
