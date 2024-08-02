package de.jonasheilig.levelSystem.listeners

import de.jonasheilig.levelSystem.LevelSystem
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.persistence.PersistentDataType
import org.bukkit.NamespacedKey
import org.bukkit.entity.Firework
import org.bukkit.FireworkEffect
import org.bukkit.FireworkEffect.Type
import org.bukkit.Color
import org.bukkit.event.EventPriority
import org.bukkit.Location

class FireworkStickListener(private val plugin: LevelSystem) : Listener {

    private val colors = listOf(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.PURPLE, Color.ORANGE)
    private val types = listOf(Type.BALL, Type.BALL_LARGE, Type.STAR, Type.CREEPER, Type.BURST)

    @EventHandler(priority = EventPriority.NORMAL)
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val player = event.player
        val itemInHand = player.inventory.itemInMainHand

        val key = NamespacedKey(plugin, "firework_stick")
        val container = itemInHand.itemMeta?.persistentDataContainer

        if (container?.has(key, PersistentDataType.BYTE) == true && itemInHand.type == Material.BLAZE_ROD) {
            spawnRandomFirework(player.location)
        }
    }

    private fun spawnRandomFirework(location: Location) {
        val world = location.world ?: return

        val firework = world.spawn(location, Firework::class.java)
        val fireworkMeta = firework.fireworkMeta ?: return

        val color = colors.random()
        val type = types.random()
        val effect = FireworkEffect.builder().withColor(color).with(type).build()

        fireworkMeta.addEffect(effect)
        fireworkMeta.power = 1
        firework.fireworkMeta = fireworkMeta

        firework.detonate()
    }
}
