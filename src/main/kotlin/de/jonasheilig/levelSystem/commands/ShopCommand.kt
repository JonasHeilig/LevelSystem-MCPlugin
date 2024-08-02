package de.jonasheilig.levelSystem.commands

import de.jonasheilig.levelSystem.LevelSystem
import de.jonasheilig.levelSystem.items.CowSpawnerStick
import de.jonasheilig.levelSystem.items.MagicStick
import de.jonasheilig.levelSystem.items.StoneBreaker
import de.jonasheilig.levelSystem.items.TeleportSword
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class ShopCommand(private val plugin: LevelSystem) : CommandExecutor, Listener {

    private val shopItems = mapOf(
        "StoneBreaker" to Pair(StoneBreaker.create(plugin), 50),
        "MagicStick" to Pair(MagicStick.create(plugin), 200),
        "CowSpawnerStick" to Pair(CowSpawnerStick.create(plugin), 1000),
        "TeleportSword" to Pair(TeleportSword.create(plugin), 500),
    )

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender is Player) {
            openShop(sender)
        }
        return true
    }

    private fun openShop(player: Player) {
        val inventory = Bukkit.createInventory(null, 9, "Shop")

        for ((name, itemData) in shopItems) {
            val (itemStack, price) = itemData
            val meta = itemStack.itemMeta
            meta?.lore = listOf("${ChatColor.BOLD}${ChatColor.BLUE}Price: ${ChatColor.GOLD}$price XP")
            itemStack.itemMeta = meta
            inventory.addItem(itemStack)
        }

        player.openInventory(inventory)
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.view.title == "Shop") {
            event.isCancelled = true
            val player = event.whoClicked as Player
            val item = event.currentItem ?: return
            val meta = item.itemMeta ?: return
            val lore = meta.lore ?: return

            val priceLine = lore.firstOrNull { it.startsWith("§6Price: §a") } ?: return
            val price = priceLine.replace("${ChatColor.BOLD}${ChatColor.BLUE}Price: ${ChatColor.GOLD}", "").replace(" XP", "").toIntOrNull() ?: return

            if (player.totalExperience >= price) {
                player.giveExp(-price)
                player.inventory.addItem(item)
                player.sendMessage("${ChatColor.GREEN}You bought a ${item.itemMeta?.displayName} for $price XP!")
            } else {
                player.sendMessage("${ChatColor.RED}You don't have enough XP to buy this item.")
            }
        }
    }
}
