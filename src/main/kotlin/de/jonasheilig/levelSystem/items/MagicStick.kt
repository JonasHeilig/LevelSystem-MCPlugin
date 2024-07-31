package de.jonasheilig.levelSystem.items

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

object MagicStick {
    fun create(): ItemStack {
        val stick = ItemStack(Material.STICK)
        val meta = stick.itemMeta

        meta?.setDisplayName("Magic Stick")

        meta?.addEnchant(Enchantment.KNOCKBACK, 255, true)
        meta?.addItemFlags(ItemFlag.HIDE_ENCHANTS)


        meta?.isUnbreakable = true
        meta?.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)

        stick.itemMeta = meta
        stick.amount = 1
        return stick
    }
}