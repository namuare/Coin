package me.mocha.economy.provider

import me.mocha.economy.exception.NoAccountException
import me.mocha.economy.exception.NotEnoughMoneyException
import org.bukkit.entity.Player
import java.util.*

interface Provider {
    fun hasAccount(player: Player): Boolean
    fun createAccount(player: Player, default: Int)

    @Throws(NoAccountException::class)
    fun getMoney(player: Player): Int

    @Throws(NoAccountException::class)
    fun setMoney(player: Player, money: Int)

    @Throws(NoAccountException::class)
    fun addMoney(player: Player, amount: Int)

    @Throws(NoAccountException::class, NotEnoughMoneyException::class)
    fun reduceMoney(player: Player, amount: Int)
}