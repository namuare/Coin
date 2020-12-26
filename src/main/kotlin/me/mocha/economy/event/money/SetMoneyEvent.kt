package me.mocha.economy.event.money

import me.mocha.economy.event.EconomyEvent
import org.bukkit.event.Cancellable
import java.util.*

class SetMoneyEvent(val playerId: UUID, var amount: Int) : EconomyEvent(), Cancellable {
    private var cancelled = false

    override fun isCancelled() = cancelled

    override fun setCancelled(cancel: Boolean) {
        this.cancelled = cancel
    }
}