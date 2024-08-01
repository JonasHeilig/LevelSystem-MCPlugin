package de.jonasheilig.levelSystem.commands

import de.jonasheilig.levelSystem.LevelSystem
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.io.IOException
import java.util.UUID

class MaxKelpCommand(private val plugin: LevelSystem) : CommandExecutor {

    private val maxKelpFile = File(plugin.dataFolder, "maxKelp.yml")
    private val maxKelpConfig = YamlConfiguration()

    init {
        if (!maxKelpFile.exists()) {
            plugin.saveResource("maxKelp.yml", false)
        }
        loadMaxKelpConfig()
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            if (!sender.hasPermission("levelsystem.maxkelp")) {
                sender.sendMessage("${ChatColor.RED}You do not have permission to use this command.")
                return true
            }

            if (args.size == 2) {
                val targetPlayer = plugin.server.getPlayer(args[0])
                val maxKelp = args[1].toIntOrNull()

                if (targetPlayer != null && maxKelp != null) {
                    setMaxKelp(targetPlayer.uniqueId, maxKelp)
                    sender.sendMessage("${ChatColor.GREEN}Max Kelp for ${targetPlayer.name} set to $maxKelp.")
                } else {
                    sender.sendMessage("${ChatColor.RED}Invalid arguments. Usage: /maxkelp <player> <max kelp>")
                }
            } else {
                sender.sendMessage("${ChatColor.RED}Usage: /maxkelp <player> <max kelp>")
            }
        } else {
            sender.sendMessage("${ChatColor.RED}Only players can use this command.")
        }
        return true
    }

    private fun setMaxKelp(uuid: UUID, maxKelp: Int) {
        maxKelpConfig.set(uuid.toString(), maxKelp)
        saveMaxKelpConfig()
    }

    private fun loadMaxKelpConfig() {
        try {
            maxKelpConfig.load(maxKelpFile)
        } catch (e: IOException) {
            plugin.logger.severe("Could not load max kelp configuration: ${e.message}")
        } catch (e: org.bukkit.configuration.InvalidConfigurationException) {
            plugin.logger.severe("Could not load max kelp configuration: ${e.message}")
        }
    }

    private fun saveMaxKelpConfig() {
        try {
            maxKelpConfig.save(maxKelpFile)
        } catch (e: IOException) {
            plugin.logger.severe("Could not save max kelp configuration: ${e.message}")
        }
    }
}
