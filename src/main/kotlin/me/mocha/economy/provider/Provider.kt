package me.mocha.economy.provider
import org.bukkit.entity.Player

interface Provider {
    fun hasAccount(player: Player): Boolean
    fun createAccount(player: Player, default: Int): Boolean
    fun getMoney(player: Player): Int
    fun setMoney(player: Player, amount: Int)
    fun addMoney(player: Player, amount: Int)
    fun reduceMoney(player: Player, amount: Int)
    fun save()
}