package de.jonasheilig.levelSystem.listeners

import de.jonasheilig.levelSystem.LevelSystem
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.util.BlockIterator
import org.bukkit.util.Vector

class TeleportSwordListener(private val plugin: LevelSystem) : Listener {

    private val noFallDamagePlayers = mutableSetOf<Player>()

    @EventHandler
    fun onPlayerUse(event: PlayerInteractEvent) {
        val player = event.player
        val item = player.inventory.itemInMainHand

        val key = NamespacedKey(plugin, "teleport_sword")

        if (item.type == Material.DIAMOND_SWORD && item.itemMeta?.persistentDataContainer?.has(key, org.bukkit.persistence.PersistentDataType.BYTE) == true) {
            val direction = player.location.direction
            val startLocation = player.location.clone()
            val targetLocation = findSafeLocation(startLocation, direction, 10)

            if (targetLocation != null) {
                targetLocation.y += 1

                targetLocation.pitch = player.location.pitch
                targetLocation.yaw = player.location.yaw

                player.teleport(targetLocation)
                noFallDamagePlayers.add(player)
                player.addPotionEffect(PotionEffect(PotionEffectType.SLOW_FALLING, 200, 1))

            } else {
                player.sendMessage("§cNo safe location found for teleportation!")
            }
        }
    }

    @EventHandler
    fun onPlayerFall(event: EntityDamageEvent) {
        if (event.entity is Player) {
            val player = event.entity as Player

            if (noFallDamagePlayers.contains(player) && event.cause == EntityDamageEvent.DamageCause.FALL) {
                event.isCancelled = true
                noFallDamagePlayers.remove(player)
            }
        }
    }

    private fun findSafeLocation(start: org.bukkit.Location, direction: Vector, distance: Int): org.bukkit.Location? {
        val iterator = BlockIterator(start, 0.0, distance)
        var lastSafeLocation: org.bukkit.Location? = null

        while (iterator.hasNext()) {
            val block = iterator.next()
            val aboveBlock = block.getRelative(org.bukkit.block.BlockFace.UP)
            val aboveAboveBlock = aboveBlock.getRelative(org.bukkit.block.BlockFace.UP)

            if (block.type == Material.AIR || aboveBlock.type == Material.AIR || aboveAboveBlock.type == Material.AIR) {
                lastSafeLocation = aboveBlock.location.add(0.5, 0.0, 0.5) // Center the player on the block
            } else if (block.type.isSolid && aboveBlock.type == Material.AIR && aboveAboveBlock.type == Material.AIR) {
                lastSafeLocation = aboveBlock.location.add(0.5, 0.0, 0.5) // Center the player on the block
            } else {
                break
            }
        }

        return lastSafeLocation
    }
}
