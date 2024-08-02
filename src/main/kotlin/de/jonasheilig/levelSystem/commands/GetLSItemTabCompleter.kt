package de.jonasheilig.levelSystem.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class GetLSItemTabCompleter : TabCompleter {
    private val items = listOf(
        "stonebreaker",
        "magicstick",
        "cowspawnerstick",
        "tp-sword",
        "daystick",
        "butchersword",
        "extrawoolshears",
        "fireworkstick",
    )

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<String>): List<String> {
        if (sender is Player) {
            if (args.size == 1) {
                return items.filter { it.startsWith(args[0], ignoreCase = true) }
            }
        }
        return emptyList()
    }
}