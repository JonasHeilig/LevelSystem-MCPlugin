package de.jonasheilig.levelSystem.listeners

import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.data.Ageable
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import de.jonasheilig.levelSystem.LevelSystem
import org.bukkit.ChatColor
import java.util.Random

class FarmListener(private val plugin: LevelSystem) : Listener {
    private val random = Random()

    private val farmMaterials = setOf(Material.WHEAT, Material.CARROTS, Material.POTATOES, Material.BEETROOTS)

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        val block = event.block
        val blockType = block.type

        if (blockType in farmMaterials && isFullyGrown(block)) {
            val player = event.player
            val playerUUID = player.uniqueId

            val farmCount = plugin.farmCounts.getOrDefault(playerUUID, 0) + 1
            plugin.farmCounts[playerUUID] = farmCount

            val level = plugin.farmLevels.getOrDefault(playerUUID, 1)
            val levelConfig = plugin.getFarmConfig(level)
            val nextLevelConfig = plugin.getFarmConfig(level + 1)

            if (nextLevelConfig != null && farmCount >= nextLevelConfig["items_required"] as Int) {
                plugin.farmLevels[playerUUID] = level + 1
                player.sendMessage("${ChatColor.GOLD}Congratulations! You've reached farming level ${level + 1}!")
            }

            val xpProbability = levelConfig?.get("xp_probability") as Double? ?: 0.0
            val xpAmount = levelConfig?.get("xp_amount") as Int? ?: 0

            if (random.nextDouble() < xpProbability) {
                player.giveExp(xpAmount)
                // player.sendMessage("${ChatColor.GOLD}You received $xpAmount XP!")
            }

            val itemsRequiredCurrent = levelConfig?.get("items_required") as Int? ?: 0
            val itemsRequiredNext = nextLevelConfig?.get("items_required") as Int? ?: itemsRequiredCurrent
            val message = "${ChatColor.GREEN}Farmer | Level: $level | $farmCount / $itemsRequiredNext Items"
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent(message))
        }
    }

    private fun isFullyGrown(block: Block): Boolean {
        val blockData = block.blockData
        return if (blockData is Ageable) {
            blockData.age == blockData.maximumAge
        } else {
            false
        }
    }
}
