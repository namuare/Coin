package me.mocha.economy.exception

import me.mocha.economy.EconomyManager
import me.mocha.economy.plugin
import org.bukkit.entity.Player

class NotEnoughMoneyException(player: Player, amount: Int) :
    EconomyException(plugin.getMessage("notenough",
        "player" to player.name, "amount" to amount, "unit" to EconomyManager.unit())) {
}