package me.mocha.economy

import me.mocha.economy.event.account.CreateAccountEvent
import me.mocha.economy.event.money.AddMoneyEvent
import me.mocha.economy.event.money.MoneyChangedEvent
import me.mocha.economy.event.money.ReduceMoneyEvent
import me.mocha.economy.event.money.SetMoneyEvent
import me.mocha.economy.exception.EconomyException
import me.mocha.economy.exception.NoAccountException
import me.mocha.economy.exception.NotEnoughMoneyException
import me.mocha.economy.provider.Provider
import org.bukkit.entity.Player

object EconomyManager {
    lateinit var provider: Provider

    fun hasAccount(player: Player) = provider.hasAccount(player)

    fun createAccount(player: Player, default: Int = defaultMoney()): Boolean {
        if (!hasAccount(player)) {
            CreateAccountEvent(player, default).callEvent()
            provider.createAccount(player, default)
            return true
        }
        return false
    }

    @Throws(NoAccountException::class)
    fun getMoney(player: Player): Int {
        return provider.getMoney(player)
    }

    @Throws(EconomyException::class, NoAccountException::class)
    fun setMoney(player: Player, amount: Int) {
        if (amount <= 0) throw IllegalArgumentException(plugin.getMessage("errors.zeromoney"))

        val event = SetMoneyEvent(player, amount)
        event.callEvent()

        if (!event.isCancelled) {
            provider.setMoney(event.player, event.amount)
            MoneyChangedEvent(event.player, getMoney(event.player)).callEvent()
        }
    }

    @Throws(IllegalArgumentException::class, NoAccountException::class)
    fun addMoney(player: Player, amount: Int) {
        if (amount <= 0) throw IllegalArgumentException(plugin.getMessage("errors.zeromoney"))

        val event = AddMoneyEvent(player, amount)
        event.callEvent()

        if (!event.isCancelled) {
            provider.addMoney(event.player, event.amount)
            MoneyChangedEvent(event.player, getMoney(event.player)).callEvent()
        }
    }

    @Throws(IllegalArgumentException::class, NoAccountException::class)
    fun reduceMoney(player: Player, amount: Int) {
        if (amount <= 0) throw IllegalArgumentException(plugin.getMessage("errors.zeromoney"))

        if (getMoney(player) - amount < 0) throw NotEnoughMoneyException(player, amount - getMoney(player))

        val event = ReduceMoneyEvent(player, amount)
        event.callEvent()

        if (!event.isCancelled) {
            provider.reduceMoney(event.player, event.amount)
            AddMoneyEvent(event.player, getMoney(event.player)).callEvent()
        }
    }


    fun defaultMoney() = plugin.config.getInt("default")
    fun unit() = plugin.config.getString("unit") ?: "$"

}