package de.jonasheilig.levelSystem.listeners

import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import de.jonasheilig.levelSystem.LevelSystem
import org.bukkit.ChatColor
import java.util.Random

class MinerListener(private val plugin: LevelSystem) : Listener {
    private val random = Random()

    // Materials
    private val oreMaterials = setOf(
        Material.COAL_ORE, Material.IRON_ORE, Material.GOLD_ORE,
        Material.DIAMOND_ORE, Material.EMERALD_ORE, Material.LAPIS_ORE,
        Material.REDSTONE_ORE, Material.NETHER_QUARTZ_ORE, Material.NETHER_GOLD_ORE,
        Material.DEEPSLATE_COAL_ORE, Material.DEEPSLATE_IRON_ORE, Material.DEEPSLATE_GOLD_ORE,
        Material.DEEPSLATE_DIAMOND_ORE, Material.DEEPSLATE_EMERALD_ORE, Material.DEEPSLATE_LAPIS_ORE,
        Material.DEEPSLATE_REDSTONE_ORE, Material.ANCIENT_DEBRIS
    )

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        val block = event.block
        val blockType = block.type

        if (blockType in oreMaterials) {
            val player = event.player
            val playerUUID = player.uniqueId

            val oreCount = plugin.stoneCounts.getOrDefault(playerUUID, 0) + 1
            plugin.stoneCounts[playerUUID] = oreCount

            val level = plugin.playerLevels.getOrDefault(playerUUID, 1)
            val levelConfig = plugin.getStoneConfig(level)
            val nextLevelConfig = plugin.getStoneConfig(level + 1)

            if (nextLevelConfig != null && oreCount >= nextLevelConfig["items_required"] as Int) {
                plugin.playerLevels[playerUUID] = level + 1
                player.sendMessage("${ChatColor.GOLD}Congratulations! You've reached mining level ${level + 1}!")
            }

            val xpProbability = levelConfig?.get("xp_probability") as Double? ?: 0.0
            val xpAmount = levelConfig?.get("xp_amount") as Int? ?: 0

            if (random.nextDouble() < xpProbability) {
                player.giveExp(xpAmount)
                // player.sendMessage("${ChatColor.GOLD}You received $xpAmount XP!")
            }

            val itemsRequiredCurrent = levelConfig?.get("items_required") as Int? ?: 0
            val itemsRequiredNext = nextLevelConfig?.get("items_required") as Int? ?: itemsRequiredCurrent
            val message = "${ChatColor.DARK_GRAY}Miner | Level: $level | $oreCount / $itemsRequiredNext Ores"
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent(message))
        }
    }
}
