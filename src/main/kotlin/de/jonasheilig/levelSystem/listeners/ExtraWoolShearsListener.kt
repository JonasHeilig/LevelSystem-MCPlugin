package de.jonasheilig.levelSystem.listeners

import de.jonasheilig.levelSystem.LevelSystem
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerShearEntityEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import org.bukkit.NamespacedKey
import org.bukkit.entity.EntityType
import org.bukkit.entity.Sheep

class ExtraWoolShearsListener(private val plugin: LevelSystem) : Listener {

    @EventHandler
    fun onPlayerShear(event: PlayerShearEntityEvent) {
        val player = event.player
        val itemInHand: ItemStack = player.inventory.itemInMainHand

        val key = NamespacedKey(plugin, "extra_wool_shears")
        val container = itemInHand.itemMeta?.persistentDataContainer

        if (container?.has(key, PersistentDataType.BYTE) == true) {
            val entity = event.entity
            if (entity.type == EntityType.SHEEP) {
                val sheep = entity as Sheep
                val color = sheep.color
                val woolMaterial = color?.let { Material.valueOf("${it.name}_WOOL") }

                if (woolMaterial != null) {
                    entity.world.dropItem(entity.location, ItemStack(woolMaterial, 2))
                }
            }
        }
    }
}
