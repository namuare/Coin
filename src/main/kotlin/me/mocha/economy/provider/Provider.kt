package me.mocha.economy.provider
import java.util.*

interface Provider {
    fun hasAccount(uuid: UUID): Boolean
    fun createAccount(uuid: UUID, default: Int): Boolean

    fun getMoney(uuid: UUID): Int
    fun setMoney(uuid: UUID, amount: Int)
    fun addMoney(uuid: UUID, amount: Int)
    fun reduceMoney(uuid: UUID, amount: Int)

    fun save()
}