package me.mocha.economy.event.money

import org.bukkit.entity.Player

class SetMoneyEvent(player: Player, amount: Int) : ChangeMoneyEvent(player, amount) {
}