package me.mocha.economy.event.money

import me.mocha.economy.EconomyManager
import org.bukkit.entity.Player

class AddMoneyEvent(player: Player, amount: Int) : ChangeMoneyEvent(player, amount) {
    override fun getResult(): Int {
        return EconomyManager.getMoney(player) + amount
    }
}