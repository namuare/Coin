package me.mocha.economy.event.money

import me.mocha.economy.event.EconomyEvent
import org.bukkit.entity.Player

class MoneyChangedEvent(val player: Player, val money: Int) : EconomyEvent() {
}