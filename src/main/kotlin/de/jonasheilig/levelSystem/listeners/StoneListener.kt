package de.jonasheilig.levelSystem.listeners

import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import de.jonasheilig.levelSystem.LevelSystem
import org.bukkit.ChatColor
import java.util.UUID
import java.util.Random

class StoneListener(private val plugin: LevelSystem) : Listener {
    private val random = Random()

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        val blockType = event.block.type
        if (blockType == Material.STONE) {
            val player = event.player
            val playerUUID = player.uniqueId

            val stoneCount = plugin.stoneCounts.getOrDefault(playerUUID, 0) + 1
            plugin.stoneCounts[playerUUID] = stoneCount

            val level = plugin.playerLevels.getOrDefault(playerUUID, 1)
            val levelConfig = plugin.getStoneConfig(level)
            val nextLevelConfig = plugin.getStoneConfig(level + 1)

            if (nextLevelConfig != null && stoneCount >= nextLevelConfig["stone_required"] as Int) {
                plugin.playerLevels[playerUUID] = level + 1
                player.sendMessage("${ChatColor.GOLD}Congratulations! You've reached level ${level + 1}!")
            }

            val xpProbability = levelConfig?.get("xp_probability") as Double? ?: 0.0
            val xpAmount = levelConfig?.get("xp_amount") as Int? ?: 0

            if (random.nextDouble() < xpProbability) {
                player.giveExp(xpAmount)
                // player.sendMessage("${ChatColor.GOLD}You received $xpAmount XP!")
            }

            val stonesRequiredCurrent = levelConfig?.get("stone_required") as Int? ?: 0
            val stonesRequiredNext = nextLevelConfig?.get("stone_required") as Int? ?: stonesRequiredCurrent

            val message = "${ChatColor.DARK_GRAY}Stoner | Level: $level | $stoneCount / $stonesRequiredNext Steine"
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent(message))
        }
    }
}
