package me.mocha.economy.exception

import org.bukkit.entity.Player
import java.util.*

class NotEnoughMoneyException : EconomyException {
    override val messagePath: String = "errors.notenough"

    constructor(message: String) : super(message)
    constructor(playerId: UUID) : this("there is not enough money of uuid $playerId")
    constructor(player: Player) : this(player.uniqueId)
}