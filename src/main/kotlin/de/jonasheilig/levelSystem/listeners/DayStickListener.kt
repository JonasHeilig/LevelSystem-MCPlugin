package de.jonasheilig.levelSystem.listeners

import de.jonasheilig.levelSystem.LevelSystem
import org.bukkit.ChatColor
import org.bukkit.NamespacedKey
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.persistence.PersistentDataType
import java.util.*

class DayStickListener(private val plugin: LevelSystem) : Listener {

    private val cooldowns: MutableMap<UUID, Long> = HashMap()

    @EventHandler
    fun onPlayerUseDayStick(event: PlayerInteractEvent) {
        val player = event.player
        val item = player.inventory.itemInMainHand

        if (item.type == Material.STICK) {
            val meta = item.itemMeta ?: return
            val key = NamespacedKey(plugin, "day_stick")
            val isDayStick = meta.persistentDataContainer.get(key, PersistentDataType.BYTE) == 1.toByte()

            if (isDayStick) {
                val currentTime = System.currentTimeMillis()
                val cooldownTime = 20 * 60 * 1000

                if (cooldowns.containsKey(player.uniqueId) && cooldowns[player.uniqueId]!! > currentTime) {
                    val timeLeft = (cooldowns[player.uniqueId]!! - currentTime) / 1000
                    player.sendMessage("${ChatColor.RED}Day Stick is on cooldown. Please wait $timeLeft seconds.")
                    return
                }

                player.world.time = 1000
                player.sendMessage("${ChatColor.GREEN}It is now day time!")

                cooldowns[player.uniqueId] = currentTime + cooldownTime
            }
        }
    }
}
