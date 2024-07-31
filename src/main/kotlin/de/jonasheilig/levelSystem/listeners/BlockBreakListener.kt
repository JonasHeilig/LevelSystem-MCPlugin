package de.jonasheilig.levelSystem.listeners

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import de.jonasheilig.levelSystem.LevelSystem

class BlockBreakListener(private val plugin: LevelSystem) : Listener {
    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        if (event.block.type == Material.STONE) {
            val player = event.player
            val playerName = player.name

            val stoneCount = plugin.stoneCounts.getOrDefault(playerName, 0)
            plugin.stoneCounts[playerName] = stoneCount + 1

            player.sendMessage("You broke a stone block! Total: ${plugin.stoneCounts[playerName]}")
        }
    }
}
