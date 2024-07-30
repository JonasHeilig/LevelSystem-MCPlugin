package de.jonasheilig.levelSystem.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class Fly : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (command.name.equals("fly", ignoreCase = true)) {
            if (sender is Player) {
                sender.allowFlight = !sender.allowFlight
                sender.sendMessage("Flight mode " + if (sender.allowFlight) "enabled" else "disabled")
                return true
            }
            sender.sendMessage("This command can only be executed by a player.")
        }
        return false
    }
}