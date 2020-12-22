package me.mocha.economy.event.money

import org.bukkit.entity.Player

class ReduceMoneyEvent(player: Player, amount: Int) : ChangeMoneyEvent(player, amount) {
}