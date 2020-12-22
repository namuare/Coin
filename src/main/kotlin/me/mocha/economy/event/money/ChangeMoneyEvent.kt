package me.mocha.economy.event.money

import me.mocha.economy.event.EconomyEvent
import org.bukkit.entity.Player

abstract class ChangeMoneyEvent(
    val player: Player,
    var amount: Int,
) : EconomyEvent() {

    abstract fun getResult(): Int

}