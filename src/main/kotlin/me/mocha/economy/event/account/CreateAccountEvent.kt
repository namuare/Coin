package me.mocha.economy.event.account

import me.mocha.economy.event.EconomyEvent
import org.bukkit.entity.Player

class CreateAccountEvent(
    val player: Player,
    var default: Int
) : EconomyEvent() {
}