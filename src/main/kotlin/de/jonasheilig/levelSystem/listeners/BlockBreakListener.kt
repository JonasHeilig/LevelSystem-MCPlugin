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

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        if (event.block.type == Material.STONE) {
            val player = event.player
            val playerUUID = player.uniqueId

            val stoneCount = plugin.stoneCounts.getOrDefault(playerUUID, 0) + 1
            plugin.stoneCounts[playerUUID] = stoneCount

            val level = plugin.playerLevels.getOrDefault(playerUUID, 1)
            val levelConfig = plugin.getStoneConfig(level)
            val nextLevelConfig = plugin.getStoneConfig(level + 1)

            if (nextLevelConfig != null && stoneCount >= nextLevelConfig["stone_required"] as Int) {
                plugin.playerLevels[playerUUID] = level + 1
                player.sendMessage("Congratulations! You've reached level ${level + 1}!")
            }

            val xpProbability = levelConfig?.get("xp_probability") as Double? ?: 0.0
            val xpAmount = levelConfig?.get("xp_amount") as Int? ?: 0

            if (random.nextDouble() < xpProbability) {
                player.giveExp(xpAmount)
                player.sendMessage("You received $xpAmount XP!")
            }

            val stonesToNextLevel = (nextLevelConfig?.get("stone_required") as Int? ?: stoneCount) - stoneCount
            val message = "Level: ${plugin.playerLevels[playerUUID]}, Stones to next level: $stonesToNextLevel"
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent(message))
        }
    }
}
