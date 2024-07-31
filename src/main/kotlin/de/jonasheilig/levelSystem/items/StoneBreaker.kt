package de.jonasheilig.levelSystem.items

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

object StoneBreaker {
    fun create(): ItemStack {
        val pickaxe = ItemStack(Material.DIAMOND_PICKAXE)
        val meta = pickaxe.itemMeta

        meta?.setDisplayName("§6StoneBreaker")

        meta?.addEnchant(Enchantment.EFFICIENCY, 5, true)
        meta?.addEnchant(Enchantment.FORTUNE, 3, true)
        meta?.addEnchant(Enchantment.UNBREAKING, 3, true)

        meta?.isUnbreakable = true
        meta?.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)

        pickaxe.itemMeta = meta
        return pickaxe
    }
}
