package de.jonasheilig.levelSystem.listeners

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.util.Vector

class TeleportSwordListener : Listener {

    private val noFallDamagePlayers = mutableSetOf<Player>()

    @EventHandler
    fun onPlayerUse(event: PlayerInteractEvent) {
        val player = event.player
        val item = player.inventory.itemInMainHand

        if (item.type == Material.DIAMOND_SWORD && item.itemMeta?.displayName == "§6Teleport Sword") {
            val direction = player.location.direction
            val targetLocation = player.location.add(direction.multiply(10))
            targetLocation.y += 1

            player.teleport(targetLocation)
            noFallDamagePlayers.add(player)
            // player.sendMessage("Teleported 10 blocks ahead!")
        }
    }

    @EventHandler
    fun onPlayerFall(event: org.bukkit.event.entity.EntityDamageEvent) {
        if (event.entity is Player) {
            val player = event.entity as Player

            if (noFallDamagePlayers.contains(player) && event.cause == org.bukkit.event.entity.EntityDamageEvent.DamageCause.FALL) {
                event.isCancelled = true
                noFallDamagePlayers.remove(player)
                // player.sendMessage("Fall damage prevented after teleport!")
            }
        }
    }
}
