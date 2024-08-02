package de.jonasheilig.levelSystem

import de.jonasheilig.levelSystem.commands.*
import de.jonasheilig.levelSystem.listeners.*
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.CommandExecutor
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin
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

    val minerCounts = mutableMapOf<UUID, Int>()
    val minerLevels = mutableMapOf<UUID, Int>()
    private val minerFile = File(dataFolder, "minerCounts.yml")
    private val minerConfigFile = File(dataFolder, "config_miner.yml")
    private val minerConfig = YamlConfiguration()

    override fun onEnable() {
        // Load and update configuration files
        updateConfig("config_stone.yml")
        updateConfig("config_farm.yml")
        updateConfig("config_miner.yml")

        // Register commands
        getCommand("fly")?.setExecutor(Fly())
        getCommand("day")?.setExecutor(SetDay())
        getCommand("get-ls")?.setExecutor(GetLSItem(this))
        getCommand("get-ls")?.tabCompleter = GetLSItemTabCompleter()
        getCommand("shop")?.setExecutor(ShopCommand(this))
        getCommand("maxkelp")?.setExecutor(MaxKelpCommand(this))

        // Register events
        Bukkit.getPluginManager().registerEvents(StoneListener(this), this)
        Bukkit.getPluginManager().registerEvents(FarmListener(this), this)
        Bukkit.getPluginManager().registerEvents(MinerListener(this), this)
        Bukkit.getPluginManager().registerEvents(KelpPlaceListener(this), this)
        Bukkit.getPluginManager().registerEvents(TeleportSwordListener(this), this)
        Bukkit.getPluginManager().registerEvents(CowSpawnerStickListener(this), this)

        logger.info("LevelSystem enabled")

        // Load data and configurations
        loadStoneCounts()
        loadStoneConfig()
        loadFarmCounts()
        loadFarmConfig()
        loadMinerCounts()
        loadMinerConfig()
    }

    override fun onDisable() {
        saveStoneCounts()
        saveFarmCounts()
        saveMinerCounts()
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
                stoneConfig.getKeys(false).forEach { key ->
                    try {
                        val uuid = UUID.fromString(key)
                        stoneCounts[uuid] = stoneConfig.getInt(key)
                    } catch (e: IllegalArgumentException) {
                        logger.warning("Ignoring non-UUID key in stoneCounts: $key")
                    }
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
            stoneCounts.forEach { (uuid, count) ->
                stoneConfig.set(uuid.toString(), count)
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
                farmConfig.getKeys(false).forEach { key ->
                    try {
                        val uuid = UUID.fromString(key)
                        farmCounts[uuid] = farmConfig.getInt(key)
                    } catch (e: IllegalArgumentException) {
                        logger.warning("Ignoring non-UUID key in farmCounts: $key")
                    }
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
            farmCounts.forEach { (uuid, count) ->
                farmConfig.set(uuid.toString(), count)
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

    private fun loadMinerCounts() {
        if (minerFile.exists()) {
            try {
                minerConfig.load(minerFile)
                minerConfig.getKeys(false).forEach { key ->
                    try {
                        val uuid = UUID.fromString(key)
                        minerCounts[uuid] = minerConfig.getInt(key)
                    } catch (e: IllegalArgumentException) {
                        logger.warning("Ignoring non-UUID key in minerCounts: $key")
                    }
                }
                logger.info("Miner counts loaded successfully.")
            } catch (e: IOException) {
                logger.severe("Could not load miner counts: ${e.message}")
            } catch (e: org.bukkit.configuration.InvalidConfigurationException) {
                logger.severe("Could not load miner counts: ${e.message}")
            }
        }
    }

    private fun saveMinerCounts() {
        try {
            minerCounts.forEach { (uuid, count) ->
                minerConfig.set(uuid.toString(), count)
            }
            minerConfig.save(minerFile)
            logger.info("Miner counts saved successfully.")
        } catch (e: IOException) {
            logger.severe("Could not save miner counts: ${e.message}")
        }
    }

    private fun loadMinerConfig() {
        if (minerConfigFile.exists()) {
            try {
                minerConfig.load(minerConfigFile)
                logger.info("Miner configuration loaded successfully.")
            } catch (e: IOException) {
                logger.severe("Could not load miner configuration: ${e.message}")
            } catch (e: org.bukkit.configuration.InvalidConfigurationException) {
                logger.severe("Could not load miner configuration: ${e.message}")
            }
        } else {
            logger.warning("Miner configuration file not found. Default values may be used.")
        }
    }

    private fun updateConfig(resource: String) {
        val file = File(dataFolder, resource)
        if (file.exists()) {
            val currentConfig = YamlConfiguration.loadConfiguration(file)
            val defaultConfig = YamlConfiguration.loadConfiguration(getResource(resource)!!.reader())

            defaultConfig.getKeys(true).forEach { key ->
                if (!currentConfig.contains(key)) {
                    currentConfig.set(key, defaultConfig.get(key))
                }
            }

            try {
                currentConfig.save(file)
                logger.info("$resource updated with new values.")
            } catch (e: IOException) {
                logger.severe("Could not update $resource: ${e.message}")
            }
        } else {
            saveResource(resource, false)
        }
    }

    fun getStoneConfig(level: Int): Map<String, Any>? {
        return stoneConfig.getConfigurationSection("levels.$level")?.getValues(false)
    }

    fun getFarmConfig(level: Int): Map<String, Any>? {
        return farmConfig.getConfigurationSection("levels.$level")?.getValues(false)
    }

    fun getMinerConfig(level: Int): Map<String, Any>? {
        return minerConfig.getConfigurationSection("levels.$level")?.getValues(false)
    }
}
