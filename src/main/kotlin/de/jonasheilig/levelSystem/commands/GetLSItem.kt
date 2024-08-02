package de.jonasheilig.levelSystem.commands

import de.jonasheilig.levelSystem.LevelSystem
import de.jonasheilig.levelSystem.items.*
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class GetLSItem(private val plugin: LevelSystem) : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender is Player) {
            if (args.isNotEmpty()) {
                val itemName = args[0].lowercase()
                val item = when (itemName) {
                    "stonebreaker" -> StoneBreaker.create(plugin)
                    "magicstick" -> MagicStick.create(plugin)
                    "cowspawnerstick" -> CowSpawnerStick.create(plugin)
                    "tp-sword" -> TeleportSword.create(plugin)
                    "daystick" -> DayStick.create(plugin)
                    "butchersword" -> ButcherSword.create(plugin)
                    "extrawoolshears" -> ExtraWoolShears.create(plugin)
                    else -> {
                        sender.sendMessage("${ChatColor.RED}Unknown item. Please specify a valid custom item.")
                        return true
                    }
                }
                sender.inventory.addItem(item)
                // sender.sendMessage("${ChatColor.GREEN}You received a ${item.itemMeta?.displayName}!")
            } else {
                sender.sendMessage("${ChatColor.RED}Usage: /get-ls <item_name>")
            }
        } else {
            sender.sendMessage("${ChatColor.RED}This command can only be used by players.")
        }
        return true
    }
}
