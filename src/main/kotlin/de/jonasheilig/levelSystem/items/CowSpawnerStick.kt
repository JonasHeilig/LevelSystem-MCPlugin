package de.jonasheilig.levelSystem.items

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.ChatColor
import de.jonasheilig.levelSystem.LevelSystem

object CowSpawnerStick {
    fun create(plugin: LevelSystem): ItemStack {
        val item = ItemStack(Material.STICK)
        val meta: ItemMeta = item.itemMeta ?: return item

        meta.setDisplayName("${ChatColor.MAGIC}-${ChatColor.LIGHT_PURPLE}Cow Spawner Stick${ChatColor.MAGIC}-")
        meta.isUnbreakable = true
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)

        val key = NamespacedKey(plugin, "cow_spawner_stick")
        meta.persistentDataContainer.set(key, org.bukkit.persistence.PersistentDataType.BYTE, 1.toByte())

        item.itemMeta = meta
        item.amount = 1
        return item
    }
}
