package de.jonasheilig.levelSystem.items

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.ChatColor
import de.jonasheilig.levelSystem.LevelSystem
import org.bukkit.persistence.PersistentDataType

object ButcherSword {
    fun create(plugin: LevelSystem): ItemStack {
        val sword = ItemStack(Material.DIAMOND_SWORD)
        val meta: ItemMeta = sword.itemMeta ?: return sword

        meta.setDisplayName("${ChatColor.RED}Butcher")

        meta.addEnchant(Enchantment.SHARPNESS, 5, true)
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
        meta.isUnbreakable = true
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)

        val key = NamespacedKey(plugin, "butcher_sword")
        meta.persistentDataContainer.set(key, PersistentDataType.BYTE, 1.toByte())

        sword.itemMeta = meta
        sword.amount = 1
        return sword
    }
}
