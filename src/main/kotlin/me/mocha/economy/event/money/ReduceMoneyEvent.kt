package me.mocha.economy.event.money

import me.mocha.economy.event.EconomyEvent
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable

class ReduceMoneyEvent(val player: Player, var amount: Int) : EconomyEvent(), Cancellable {
    private var cancelled = false

    override fun isCancelled() = cancelled

    override fun setCancelled(cancel: Boolean) {
        this.cancelled = cancel
    }
}