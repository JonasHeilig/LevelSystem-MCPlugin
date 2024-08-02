package de.jonasheilig.levelSystem.items

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.ChatColor
import de.jonasheilig.levelSystem.LevelSystem

object MagicStick {
    fun create(plugin: LevelSystem): ItemStack {
        val stick = ItemStack(Material.STICK)
        val meta: ItemMeta = stick.itemMeta ?: return stick

        meta.setDisplayName("${ChatColor.MAGIC}-${ChatColor.LIGHT_PURPLE}Magic Stick${ChatColor.MAGIC}-")

        meta.addEnchant(Enchantment.KNOCKBACK, 255, true)
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
        meta.isUnbreakable = true
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)

        val key = NamespacedKey(plugin, "magic_stick")
        meta.persistentDataContainer.set(key, org.bukkit.persistence.PersistentDataType.BYTE, 1.toByte())

        stick.itemMeta = meta
        stick.amount = 1
        return stick
    }
}
