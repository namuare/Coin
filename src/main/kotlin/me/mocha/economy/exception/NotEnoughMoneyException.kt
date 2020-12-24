package me.mocha.economy.exception

import me.mocha.economy.plugin
import org.bukkit.entity.Player

class NotEnoughMoneyException(player: Player, need: Int) :
    EconomyException(plugin.getMessage("errors.noaccount", "player" to player.name, "need" to need)) {
}