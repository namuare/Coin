package me.mocha.economy

import me.mocha.economy.event.account.CreateAccountEvent
import me.mocha.economy.event.money.AddMoneyEvent
import me.mocha.economy.event.money.ReduceMoneyEvent
import me.mocha.economy.event.money.SetMoneyEvent
import me.mocha.economy.provider.Provider
import org.bukkit.entity.Player

object EconomyManager {
    lateinit var provider: Provider

    fun hasAccount(player: Player) = provider.hasAccount(player)

    fun createAccount(player: Player, default: Int = defaultMoney()): Boolean {
        if (!hasAccount(player)) {
            val event = CreateAccountEvent(player, default)
            event.callEvent()

            if (!event.isCancelled) {
                provider.createAccount(event.player, event.default)
                return true
            }
        }
        return false
    }

    fun getMoney(player: Player): Int {
        return provider.getMoney(player)
    }

    fun addMoney(player: Player, amount: Int) {
        val event = AddMoneyEvent(player, amount)
        event.callEvent()

        if (!event.isCancelled) {
            provider.addMoney(player, event.amount)
        }
    }

    fun reduceMoney(player: Player, amount: Int) {
        val event = ReduceMoneyEvent(player, amount)
        event.callEvent()

        if (!event.isCancelled) {
            provider.reduceMoney(player, event.amount)
        }
    }

    fun setMoney(player: Player, amount: Int) {
        val event = SetMoneyEvent(player, amount)
        event.callEvent()

        if (!event.isCancelled) {
            provider.setMoney(player, event.amount)
        }
    }

    fun defaultMoney() = plugin.config.getInt("default")

    fun unit() = plugin.config.getString("unit") ?: "$"

}