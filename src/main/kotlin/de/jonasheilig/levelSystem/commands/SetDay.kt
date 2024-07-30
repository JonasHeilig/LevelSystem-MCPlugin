package de.jonasheilig.levelSystem.commands

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SetDay : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (command.name.equals("day", ignoreCase = true)) {
            if (sender is Player) {
                val player = sender
                val world = player.world
                world.time = 1000
                player.sendMessage("Time set to day")
            } else {
                val world = Bukkit.getWorlds().firstOrNull()
                if (world != null) {
                    world.time = 1000
                    sender.sendMessage("Time set to day in the default world")
                } else {
                    sender.sendMessage("No world found to set the time.")
                }
            }
            return true
        }
        return false
    }
}
