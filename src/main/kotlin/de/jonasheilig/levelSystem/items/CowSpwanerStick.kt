package de.jonasheilig.levelSystem.items

import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

object CowSpawnerStick : Listener {
    fun create(): ItemStack {
        val item = ItemStack(Material.STICK)
        val meta = item.itemMeta

        meta?.setDisplayName("§6Cow Spawner Stick")
        meta?.isUnbreakable = true
        meta?.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)

        item.itemMeta = meta
        item.amount = 1
        return item
    }

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val player = event.player
        val item = player.inventory.itemInMainHand

        if (item.itemMeta?.displayName == "§6Cow Spawner Stick" && event.action.name.contains("RIGHT_CLICK")) {
            val location = player.location
            player.world.spawnEntity(location, EntityType.COW)
            event.isCancelled = true
        }
    }
}