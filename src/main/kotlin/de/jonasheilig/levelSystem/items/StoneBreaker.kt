package de.jonasheilig.levelSystem.items

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import de.jonasheilig.levelSystem.LevelSystem
import org.bukkit.ChatColor

object StoneBreaker {
    fun create(plugin: LevelSystem): ItemStack {
        val pickaxe = ItemStack(Material.DIAMOND_PICKAXE)
        val meta: ItemMeta = pickaxe.itemMeta ?: return pickaxe

        meta.setDisplayName("${ChatColor.DARK_GRAY}${ChatColor.BOLD}StoneBreaker")

        meta.addEnchant(Enchantment.EFFICIENCY, 5, true)
        meta.addEnchant(Enchantment.FORTUNE, 3, true)
        meta.addEnchant(Enchantment.UNBREAKING, 3, true)
        meta.isUnbreakable = true
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)

        val key = NamespacedKey(plugin, "stone_breaker")
        meta.persistentDataContainer.set(key, org.bukkit.persistence.PersistentDataType.BYTE, 1.toByte())

        pickaxe.itemMeta = meta
        return pickaxe
    }
}
