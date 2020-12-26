package me.mocha.economy.exception

import org.bukkit.entity.Player
import java.util.*

class NoAccountException : EconomyException {
    override val messagePath: String = "errors.noaccount"

    constructor(message: String) : super(message)
    constructor(playerId: UUID) : this("could not find account by uuid $playerId")
    constructor(player: Player) : this(player.uniqueId)
}