package de.jonasheilig.levelSystem

import de.jonasheilig.levelSystem.commands.Fly
import de.jonasheilig.levelSystem.commands.SetDay
import de.jonasheilig.levelSystem.listeners.BlockBreakListener
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.CommandExecutor
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.event.EventHandler
import java.io.File
import java.io.IOException
import java.util.UUID

class LevelSystem : JavaPlugin(), Listener, CommandExecutor {

    val stoneCounts = mutableMapOf<UUID, Int>()
    private val stoneFile = File(dataFolder, "stoneCounts.yml")
    private val stoneConfig = YamlConfiguration()

    override fun onEnable() {
        saveDefaultConfig()
        getCommand("fly")?.setExecutor(Fly())
        getCommand("day")?.setExecutor(SetDay())
        Bukkit.getPluginManager().registerEvents(this, this)
        Bukkit.getPluginManager().registerEvents(BlockBreakListener(this), this)
        logger.info("LevelSystem enabled")

        loadStoneCounts()
    }

    override fun onDisable() {
        saveStoneCounts()
        logger.info("Thanks for using LevelSystem")
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        Bukkit.broadcastMessage("${ChatColor.GOLD}Player ${event.player.name} joined.")
    }

    private fun loadStoneCounts() {
        if (stoneFile.exists()) {
            try {
                stoneConfig.load(stoneFile)
                for (key in stoneConfig.getKeys(false)) {
                    val uuid = UUID.fromString(key)
                    stoneCounts[uuid] = stoneConfig.getInt(key)
                }
                logger.info("Stone counts loaded successfully.")
            } catch (e: IOException) {
                logger.severe("Could not load stone counts: ${e.message}")
            } catch (e: org.bukkit.configuration.InvalidConfigurationException) {
                logger.severe("Could not load stone counts: ${e.message}")
            }
        }
    }

    private fun saveStoneCounts() {
        try {
            for ((uuid, count) in stoneCounts) {
                stoneConfig.set(uuid.toString(), count)
            }
            stoneConfig.save(stoneFile)
            logger.info("Stone counts saved successfully.")
        } catch (e: IOException) {
            logger.severe("Could not save stone counts: ${e.message}")
        }
    }
}
