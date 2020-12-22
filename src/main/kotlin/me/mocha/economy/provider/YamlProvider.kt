package me.mocha.economy.provider

import me.mocha.economy.exception.NoAccountException
import me.mocha.economy.exception.NotEnoughMoneyException
import me.mocha.economy.plugin
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import java.io.File
import java.lang.IllegalArgumentException

class YamlProvider : Provider {
    private val moneyFile = File(plugin.dataFolder, "money.yml")
    private val money: FileConfiguration = YamlConfiguration.loadConfiguration(this.moneyFile)

    override fun hasAccount(player: Player): Boolean {
        val uid = player.uniqueId.toString()
        return this.money.contains(uid)
    }

    override fun createAccount(player: Player, default: Int) {
        val uid = player.uniqueId.toString()
        if (!hasAccount(player)) {
            this.money[uid] = default
            save()
        }
    }

    override fun getMoney(player: Player): Int {
        if (hasAccount(player)) {
            val uid = player.uniqueId.toString()
            return this.money.getInt(uid)
        } else {
            throw NoAccountException(player)
        }
    }

    override fun setMoney(player: Player, money: Int) {
        if (money < 0) {
            throw IllegalArgumentException("money must greater than zero")
        }

        if (hasAccount(player)) {
            val uid = player.uniqueId.toString()
            this.money[uid] = if (money < 0) 0 else money
            save()
        } else {
            throw NoAccountException(player)
        }
    }

    override fun addMoney(player: Player, amount: Int) {
        if (amount < 0) {
            throw IllegalArgumentException("amount must greater than zero")
        }

        if (hasAccount(player)) {
            val money = getMoney(player) + amount
            val uid = player.uniqueId.toString()
            this.money[uid] = money
            save()
        }
    }

    override fun reduceMoney(player: Player, amount: Int) {
        if (amount < 0) {
            throw IllegalArgumentException("amount must greater than zero")
        }

        if (hasAccount(player)) {
            val money = getMoney(player) - amount

            if (money >= 0) {
                val uid = player.uniqueId.toString()
                this.money[uid] = money
                save()
            } else {
                throw NotEnoughMoneyException(player, money * -1)
            }
        }
    }

    private fun save() {
        money.save(moneyFile)
    }

}