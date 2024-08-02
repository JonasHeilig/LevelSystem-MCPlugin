package de.jonasheilig.levelSystem.listeners

import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import de.jonasheilig.levelSystem.LevelSystem
import org.bukkit.ChatColor
import org.bukkit.NamespacedKey

class CowSpawnerStickListener(private val plugin: LevelSystem) : Listener {

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val player = event.player
        val item = player.inventory.itemInMainHand

        if (isCowSpawnerStick(item)) {
            if (event.action.name.contains("RIGHT_CLICK")) {
                val location = player.location
                player.world.spawnEntity(location, EntityType.COW)
                event.isCancelled = true
            }
        }
    }

    private fun isCowSpawnerStick(item: ItemStack): Boolean {
        val meta = item.itemMeta ?: return false
        val key = NamespacedKey(plugin, "cow_spawner_stick")
        return meta.displayName == "${ChatColor.MAGIC}-${ChatColor.LIGHT_PURPLE}Cow Spawner Stick${ChatColor.MAGIC}-" &&
               meta.persistentDataContainer.has(key, PersistentDataType.BYTE)
    }
}
