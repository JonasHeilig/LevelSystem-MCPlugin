package de.jonasheilig.levelSystem.items

import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import de.jonasheilig.levelSystem.LevelSystem

object TeleportSword {
    fun create(plugin: LevelSystem): ItemStack {
        val sword = ItemStack(Material.DIAMOND_SWORD)
        val meta: ItemMeta = sword.itemMeta ?: return sword

        meta.setDisplayName("${ChatColor.GOLD}${ChatColor.BOLD}Teleport Sword")
        meta.addEnchant(Enchantment.CHANNELING, 1, true)
        meta.isUnbreakable = true
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE)

        val key = NamespacedKey(plugin, "teleport_sword")
        meta.persistentDataContainer.set(key, org.bukkit.persistence.PersistentDataType.INTEGER, 1)

        sword.itemMeta = meta
        return sword
    }
}
