package de.jonasheilig.levelSystem.items

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.ChatColor
import de.jonasheilig.levelSystem.LevelSystem

object ExtraWoolShears {
    fun create(plugin: LevelSystem): ItemStack {
        val shears = ItemStack(Material.SHEARS)
        val meta: ItemMeta = shears.itemMeta ?: return shears

        meta.setDisplayName("${ChatColor.WHITE}Wool Shears")

        meta.isUnbreakable = true
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)

        val key = NamespacedKey(plugin, "extra_wool_shears")
        meta.persistentDataContainer.set(key, org.bukkit.persistence.PersistentDataType.BYTE, 1.toByte())

        shears.itemMeta = meta
        shears.amount = 1
        return shears
    }
}
