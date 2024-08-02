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

class NightStickListener(private val plugin: LevelSystem) : Listener {

    private val cooldowns: MutableMap<UUID, Long> = HashMap()

    @EventHandler
    fun onPlayerUseNightStick(event: PlayerInteractEvent) {
        val player = event.player
        val item = player.inventory.itemInMainHand

        if (item.type == Material.STICK) {
            val meta = item.itemMeta ?: return
            val key = NamespacedKey(plugin, "night_stick")
            val isNightStick = meta.persistentDataContainer.get(key, PersistentDataType.BYTE) == 1.toByte()

            if (isNightStick) {
                val currentTime = System.currentTimeMillis()
                val cooldownMinutes = plugin.config.getInt("nightstick.cooldown_minutes", 20)
                val cooldownTime = cooldownMinutes * 60 * 1000

                if (cooldowns.containsKey(player.uniqueId) && cooldowns[player.uniqueId]!! > currentTime) {
                    val timeLeft = (cooldowns[player.uniqueId]!! - currentTime) / 1000
                    val minutesLeft = timeLeft / 60
                    val secondsLeft = timeLeft % 60
                    player.sendMessage("${ChatColor.RED}Night Stick is on cooldown. Please wait $minutesLeft minutes and $secondsLeft seconds.")
                    return
                }

                player.world.time = 13000
                player.sendMessage("${ChatColor.GREEN}It is now night time!")

                cooldowns[player.uniqueId] = currentTime + cooldownTime
            }
        }
    }
}
