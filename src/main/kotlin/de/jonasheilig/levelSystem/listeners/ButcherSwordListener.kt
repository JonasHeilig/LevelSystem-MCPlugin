package de.jonasheilig.levelSystem.listeners

import de.jonasheilig.levelSystem.LevelSystem
import org.bukkit.event.EventHandler
import org.bukkit.NamespacedKey
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.inventory.ItemStack

class ButcherSwordListener(private val plugin: LevelSystem) : Listener {

    @EventHandler
    fun onEntityDeath(event: EntityDeathEvent) {
        val killer = event.entity.killer ?: return
        val itemInHand: ItemStack = killer.inventory.itemInMainHand

        val key = NamespacedKey(plugin, "butcher_sword")
        val container = itemInHand.itemMeta?.persistentDataContainer

        if (container?.has(key, org.bukkit.persistence.PersistentDataType.BYTE) == true) {
            event.drops.forEach { it.amount *= 2 }
        }
    }
}
