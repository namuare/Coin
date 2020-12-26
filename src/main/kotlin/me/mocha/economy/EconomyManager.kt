package me.mocha.economy

import me.mocha.economy.event.account.CreateAccountEvent
import me.mocha.economy.event.money.AddMoneyEvent
import me.mocha.economy.event.money.MoneyChangedEvent
import me.mocha.economy.event.money.ReduceMoneyEvent
import me.mocha.economy.event.money.SetMoneyEvent
import me.mocha.economy.exception.MoneyBelowZeroException
import me.mocha.economy.exception.NoAccountException
import me.mocha.economy.exception.NotEnoughMoneyException
import me.mocha.economy.provider.Provider
import org.bukkit.entity.Player
import java.util.*

object EconomyManager {
    lateinit var provider: Provider

    fun hasAccount(uuid: UUID): Boolean {
        return provider.hasAccount(uuid)
    }

    fun hasAccount(player: Player): Boolean {
        return hasAccount(player.uniqueId)
    }

    fun createAccount(player: Player, default: Int = defaultMoney()): Boolean {
        if (!hasAccount(player)) {
            CreateAccountEvent(player, default).callEvent()
            provider.createAccount(player.uniqueId, default)
            return true
        }
        return false
    }

    @Throws(NoAccountException::class)
    fun getMoney(uuid: UUID): Int {
        return provider.getMoney(uuid)
    }

    @Throws(NoAccountException::class)
    fun getMoney(player: Player): Int {
        return getMoney(player.uniqueId)
    }

    @Throws(MoneyBelowZeroException::class, NoAccountException::class)
    fun setMoney(uuid: UUID, amount: Int) {
        if (amount <= 0) throw MoneyBelowZeroException()

        val event = SetMoneyEvent(uuid, amount)
        event.callEvent()

        if (!event.isCancelled) {
            provider.setMoney(event.playerId, event.amount)
            MoneyChangedEvent(event.playerId, getMoney(event.playerId)).callEvent()
        }
    }

    @Throws(MoneyBelowZeroException::class, NoAccountException::class)
    fun setMoney(player: Player, amount: Int) {
        setMoney(player.uniqueId, amount)
    }

    @Throws(MoneyBelowZeroException::class, NoAccountException::class)
    fun addMoney(uuid:UUID, amount: Int) {
        if (amount <= 0) throw MoneyBelowZeroException()

        val event = AddMoneyEvent(uuid, amount)
        event.callEvent()

        if (!event.isCancelled) {
            provider.addMoney(event.playerId, event.amount)
            MoneyChangedEvent(event.playerId, getMoney(event.playerId)).callEvent()
        }
    }

    @Throws(MoneyBelowZeroException::class, NoAccountException::class)
    fun addMoney(player: Player, amount: Int) {
        addMoney(player.uniqueId, amount)
    }

    @Throws(MoneyBelowZeroException::class, NotEnoughMoneyException::class, NoAccountException::class)
    fun reduceMoney(uuid: UUID, amount: Int) {
        if (amount <= 0) throw MoneyBelowZeroException()
        val event = ReduceMoneyEvent(uuid, amount)
        event.callEvent()

        if (getMoney(uuid) - event.amount < 0) throw NotEnoughMoneyException(uuid)

        if (!event.isCancelled) {
            provider.reduceMoney(event.playerId, event.amount)
            AddMoneyEvent(event.playerId, getMoney(event.playerId)).callEvent()
        }
    }

    @Throws(MoneyBelowZeroException::class, NotEnoughMoneyException::class, NoAccountException::class)
    fun reduceMoney(player: Player, amount: Int) {
        reduceMoney(player.uniqueId, amount)
    }

    fun defaultMoney() = plugin.config.getInt("default")
    fun unit() = plugin.config.getString("unit") ?: "$"
}