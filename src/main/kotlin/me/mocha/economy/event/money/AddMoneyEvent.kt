package me.mocha.economy.event.money

import org.bukkit.entity.Player

class AddMoneyEvent(player: Player, amount: Int) : ChangeMoneyEvent(player, amount) {
}