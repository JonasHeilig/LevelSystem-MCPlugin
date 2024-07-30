package de.jonasheilig.levelSystem

import de.jonasheilig.levelSystem.commands.Fly
import de.jonasheilig.levelSystem.commands.SetDay
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.CommandExecutor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin

class LevelSystem : JavaPlugin(), Listener, CommandExecutor {

    override fun onEnable() {
        getCommand("fly")?.setExecutor(Fly())
        getCommand("day")?.setExecutor(SetDay())
        Bukkit.getPluginManager().registerEvents(this, this)
        logger.info("LevelSystem enabled")
    }

    override fun onDisable() {
        logger.info("Thanks for using LevelSystem")
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        Bukkit.broadcastMessage("${ChatColor.GOLD}Player ${event.player.name} joined.")
        event.player.sendMessage("Hello Player")
    }
}