package de.jonasheilig.levelSystem.listeners

import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import de.jonasheilig.levelSystem.LevelSystem
import java.util.Random

class BlockBreakListener(private val plugin: LevelSystem) : Listener {
    private val random = Random()
    private val xpProbability = 0.6

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        if (event.block.type == Material.STONE) {
            val player = event.player
            val playerUUID = player.uniqueId

            val stoneCount = plugin.stoneCounts.getOrDefault(playerUUID, 0)
            plugin.stoneCounts[playerUUID] = stoneCount + 1

            val message = "You broke a stone block! Total: ${plugin.stoneCounts[playerUUID]}"
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent(message))

            if (random.nextDouble() < xpProbability) {
                val xpAmount = 10
                player.giveExp(xpAmount)
                player.sendMessage("You received $xpAmount XP!")
            }
        }
    }
}
