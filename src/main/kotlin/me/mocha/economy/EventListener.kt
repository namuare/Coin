package me.mocha.economy

import me.mocha.economy.cash.CashManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.CraftItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent

object EventListener : Listener {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        if (!EconomyManager.hasAccount(event.player)) {
            plugin.logger.info("User account of ${event.player.displayName} is not found. Creating new account...")
            EconomyManager.createAccount(event.player)
        }
    }

    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        val player = event.player
        if (CashManager.isCash(player.inventory.itemInMainHand)) {
            val item = player.inventory.itemInMainHand
            player.inventory.removeItem(item)
            EconomyManager.addMoney(player, CashManager.getMoney(item))
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onCraft(event: CraftItemEvent) {
        event.inventory.matrix.forEach {
            it?.let { if (CashManager.isCash(it)) event.isCancelled = true }
        }
    }

}