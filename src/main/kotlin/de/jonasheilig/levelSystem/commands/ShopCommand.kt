package de.jonasheilig.levelSystem.commands

import de.jonasheilig.levelSystem.items.CowSpawnerStick
import de.jonasheilig.levelSystem.items.MagicStick
import de.jonasheilig.levelSystem.items.StoneBreaker
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class ShopCommand(private val plugin: JavaPlugin) : CommandExecutor, Listener {

    private val shopItems = mapOf(
        "StoneBreaker" to Pair(StoneBreaker.create(), 50),
        "MagicStick" to Pair(MagicStick.create(), 200),
        "CowSpawnerStick" to Pair(CowSpawnerStick.create(), 1000)
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
            meta?.lore = listOf("§6Price: §a$price XP")
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
            val price = priceLine.replace("§6Price: §a", "").replace(" XP", "").toIntOrNull() ?: return

            if (player.totalExperience >= price) {
                player.giveExp(-price)
                player.inventory.addItem(item)
                player.sendMessage("§aYou bought a ${item.itemMeta?.displayName} for $price XP!")
            } else {
                player.sendMessage("§cYou don't have enough XP to buy this item.")
            }
        }
    }
}
