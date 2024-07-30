package de.jonasheilig.levelSystem

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin

class LevelSystem : JavaPlugin(), Listener, CommandExecutor {

    override fun onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this)
        logger.info("LevelSystem enabled")
    }

    override fun onDisable() {
        logger.info("Thanks for using LevelSystem")
    }

    @EventHandler
    public fun onPlayerJoin(event: PlayerJoinEvent) {
        Bukkit.broadcastMessage("${ChatColor.GOLD}Player ${event.player.name} joined.")
        event.player.sendMessage("Hello Player")
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            when (command.name) {
                "day" -> {
                    sender.world.time = 1000
                    sender.sendMessage("Time set to day")
                    return true
                }
            }
        }
        return false
    }
}
