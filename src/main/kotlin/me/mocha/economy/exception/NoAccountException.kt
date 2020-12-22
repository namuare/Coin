package me.mocha.economy.exception

import me.mocha.economy.plugin
import org.bukkit.entity.Player

class NoAccountException(player: Player) : EconomyException(plugin.getMessage("noaccount", "player" to player.name)) {
}