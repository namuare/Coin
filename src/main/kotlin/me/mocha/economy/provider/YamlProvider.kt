package me.mocha.economy.provider

import me.mocha.economy.exception.NoAccountException
import me.mocha.economy.plugin
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import java.io.File

class YamlProvider : Provider {
    private val moneyFile = File(plugin.dataFolder, "money.yml")
    private val money: FileConfiguration = YamlConfiguration.loadConfiguration(moneyFile)

    override fun hasAccount(player: Player): Boolean {
        val uid = player.uniqueId.toString()
        return money.contains(uid)
    }

    override fun createAccount(player: Player, default: Int): Boolean {
        val uid = player.uniqueId.toString()
        if (!hasAccount(player)) {
            money[uid] = default
            return true
        }
        return false
    }

    override fun getMoney(player: Player): Int {
        if (hasAccount(player)) {
            val uid = player.uniqueId.toString()
            return money.getInt(uid)
        } else {
            throw NoAccountException(player)
        }
    }

    override fun setMoney(player: Player, amount: Int) {
        if (hasAccount(player)) {
            val uid = player.uniqueId.toString()
            money[uid] = amount
        } else {
            throw NoAccountException(player)
        }
    }

    override fun addMoney(player: Player, amount: Int) {
        if (hasAccount(player)) {
            val uid = player.uniqueId.toString()
            money[uid] = money.getInt(uid) + amount
        } else {
            throw NoAccountException(player)
        }
    }

    override fun reduceMoney(player: Player, amount: Int) {
        if (hasAccount(player)) {
            val uid = player.uniqueId.toString()
            money[uid] = money.getInt(uid) - amount
        } else {
            throw NoAccountException(player)
        }
    }

    override fun save() {
        money.save(moneyFile)
    }
}