package de.jonasheilig.levelSystem.commands

import de.jonasheilig.levelSystem.LevelSystem
import de.jonasheilig.levelSystem.items.CowSpawnerStick
import de.jonasheilig.levelSystem.items.MagicStick
import de.jonasheilig.levelSystem.items.StoneBreaker
import de.jonasheilig.levelSystem.items.TeleportSword
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class GetLSItem : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender is Player) {
            if (args.isNotEmpty()) {
                val itemName = args[0].lowercase()
                val item = when (itemName) {
                    "stonebraker" -> StoneBreaker.create()
                    "magicstick" -> MagicStick.create()
                    "cowspawnerstick" -> CowSpawnerStick.create()
                    "tp-sword" -> TeleportSword.create(LevelSystem())
                    else -> {
                        sender.sendMessage("§cUnknown item. Please specify a valid custom item.")
                        return true
                    }
                }
                sender.inventory.addItem(item)
                sender.sendMessage("§aYou received a ${item.itemMeta?.displayName}!")
            } else {
                sender.sendMessage("§cUsage: /get-ls <item_name>")
            }
        } else {
            sender.sendMessage("§cThis command can only be used by players.")
        }
        return true
    }
}
