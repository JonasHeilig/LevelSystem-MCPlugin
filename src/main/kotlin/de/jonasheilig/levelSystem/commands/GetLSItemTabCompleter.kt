package de.jonasheilig.levelSystem.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class GetLSItemTabCompleter : TabCompleter {
    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<String>): List<String> {
        if (sender is Player && args.isEmpty()) {
            return listOf("stonebreaker", "magicstick")
        }
        return emptyList()
    }
}
