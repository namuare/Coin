package me.mocha.economy.exception

import me.mocha.economy.plugin
import org.bukkit.entity.Player

class NoAccountException(val player: Player) :
    EconomyException(plugin.getMessage("errors.noaccount", "player" to player.name)) {
}