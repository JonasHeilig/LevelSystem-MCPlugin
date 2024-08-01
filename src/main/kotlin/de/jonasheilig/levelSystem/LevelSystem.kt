package de.jonasheilig.levelSystem

import de.jonasheilig.levelSystem.commands.Fly
import de.jonasheilig.levelSystem.commands.GetLSItem
import de.jonasheilig.levelSystem.commands.GetLSItemTabCompleter
import de.jonasheilig.levelSystem.commands.SetDay
import de.jonasheilig.levelSystem.listeners.StoneListener
import de.jonasheilig.levelSystem.listeners.FarmListener
import de.jonasheilig.levelSystem.items.CowSpawnerStick
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.CommandExecutor
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.event.EventHandler
import java.io.File
import java.io.IOException
import java.util.UUID

class LevelSystem : JavaPlugin(), Listener, CommandExecutor {

    val stoneCounts = mutableMapOf<UUID, Int>()
    val playerLevels = mutableMapOf<UUID, Int>()
    private val stoneFile = File(dataFolder, "stoneCounts.yml")
    private val stoneConfigFile = File(dataFolder, "config_stone.yml")
    private val stoneConfig = YamlConfiguration()

    val farmCounts = mutableMapOf<UUID, Int>()
    val farmLevels = mutableMapOf<UUID, Int>()
    private val farmFile = File(dataFolder, "farmCounts.yml")
    private val farmConfigFile = File(dataFolder, "config_farm.yml")
    private val farmConfig = YamlConfiguration()

    override fun onEnable() {
        saveDefaultConfig()
        // Register commands
        getCommand("fly")?.setExecutor(Fly())
        getCommand("day")?.setExecutor(SetDay())
        getCommand("get-ls")?.setExecutor(GetLSItem())
        getCommand("get-ls")?.tabCompleter = GetLSItemTabCompleter()
        // Register events
        Bukkit.getPluginManager().registerEvents(this, this)
        Bukkit.getPluginManager().registerEvents(StoneListener(this), this)
        Bukkit.getPluginManager().registerEvents(FarmListener(this), this)
        Bukkit.getPluginManager().registerEvents(CowSpawnerStick, this)
        logger.info("LevelSystem enabled")

        loadStoneCounts()
        loadStoneConfig()
        loadFarmCounts()
        loadFarmConfig()
    }

    override fun onDisable() {
        saveStoneCounts()
        saveFarmCounts()
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
                    playerLevels[uuid] = stoneConfig.getInt("$key.level", 1)
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
                stoneConfig.set("$uuid.level", playerLevels[uuid])
            }
            stoneConfig.save(stoneFile)
            logger.info("Stone counts saved successfully.")
        } catch (e: IOException) {
            logger.severe("Could not save stone counts: ${e.message}")
        }
    }

    private fun loadStoneConfig() {
        if (stoneConfigFile.exists()) {
            try {
                stoneConfig.load(stoneConfigFile)
                logger.info("Stone configuration loaded successfully.")
            } catch (e: IOException) {
                logger.severe("Could not load stone configuration: ${e.message}")
            } catch (e: org.bukkit.configuration.InvalidConfigurationException) {
                logger.severe("Could not load stone configuration: ${e.message}")
            }
        } else {
            logger.warning("Stone configuration file not found. Default values may be used.")
        }
    }

    private fun loadFarmCounts() {
        if (farmFile.exists()) {
            try {
                farmConfig.load(farmFile)
                for (key in farmConfig.getKeys(false)) {
                    val uuid = UUID.fromString(key)
                    farmCounts[uuid] = farmConfig.getInt(key)
                    farmLevels[uuid] = farmConfig.getInt("$key.level", 1)
                }
                logger.info("Farm counts loaded successfully.")
            } catch (e: IOException) {
                logger.severe("Could not load farm counts: ${e.message}")
            } catch (e: org.bukkit.configuration.InvalidConfigurationException) {
                logger.severe("Could not load farm counts: ${e.message}")
            }
        }
    }

    private fun saveFarmCounts() {
        try {
            for ((uuid, count) in farmCounts) {
                farmConfig.set(uuid.toString(), count)
                farmConfig.set("$uuid.level", farmLevels[uuid])
            }
            farmConfig.save(farmFile)
            logger.info("Farm counts saved successfully.")
        } catch (e: IOException) {
            logger.severe("Could not save farm counts: ${e.message}")
        }
    }

    private fun loadFarmConfig() {
        if (farmConfigFile.exists()) {
            try {
                farmConfig.load(farmConfigFile)
                logger.info("Farm configuration loaded successfully.")
            } catch (e: IOException) {
                logger.severe("Could not load farm configuration: ${e.message}")
            } catch (e: org.bukkit.configuration.InvalidConfigurationException) {
                logger.severe("Could not load farm configuration: ${e.message}")
            }
        } else {
            logger.warning("Farm configuration file not found. Default values may be used.")
        }
    }

    fun getStoneConfig(level: Int): Map<String, Any>? {
        return stoneConfig.getConfigurationSection("levels.$level")?.getValues(false)
    }

    fun getFarmConfig(level: Int): Map<String, Any>? {
        return farmConfig.getConfigurationSection("levels.$level")?.getValues(false)
    }
}
