package me.mocha.economy.event.money

import me.mocha.economy.event.EconomyEvent
import org.bukkit.entity.Player

open class ChangeMoneyEvent(
    val player: Player,
    val amount: Int
) : EconomyEvent() {
}