package de.jonasheilig.levelSystem.listeners

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import de.jonasheilig.levelSystem.LevelSystem
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.ChatMessageType
import java.io.File
import java.io.IOException
import org.bukkit.configuration.file.YamlConfiguration

class KelpPlaceListener(private val plugin: LevelSystem) : Listener {

    private val kelpFile = File(plugin.dataFolder, "kelpCounts.yml")
    private val kelpConfig = YamlConfiguration()

    init {
        if (!kelpFile.exists()) {
            plugin.saveResource("kelpCounts.yml", false)
        }
        loadKelpCounts()
    }

    @EventHandler
    fun onBlockPlace(event: BlockPlaceEvent) {
        val player = event.player
        if (event.blockPlaced.type == Material.KELP) {
            val uuid = player.uniqueId
            val currentCount = kelpConfig.getInt(uuid.toString(), 0) + 1
            kelpConfig.set(uuid.toString(), currentCount)
            saveKelpCounts()

            sendActionBar(player, "Kelp: $currentCount / ${getMaxKelp(player)}")
        }
    }

    private fun loadKelpCounts() {
        try {
            kelpConfig.load(kelpFile)
        } catch (e: IOException) {
            plugin.logger.severe("Could not load kelp counts: ${e.message}")
        } catch (e: org.bukkit.configuration.InvalidConfigurationException) {
            plugin.logger.severe("Could not load kelp counts: ${e.message}")
        }
    }

    private fun saveKelpCounts() {
        try {
            kelpConfig.save(kelpFile)
        } catch (e: IOException) {
            plugin.logger.severe("Could not save kelp counts: ${e.message}")
        }
    }

    private fun sendActionBar(player: Player, message: String) {
        val textComponent = TextComponent(message)
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, textComponent)
    }

    private fun getMaxKelp(player: Player): Int {
        val config = plugin.config
        return config.getInt("maxKelp.default", 64)
    }
}
