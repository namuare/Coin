package me.mocha.economy.provider

import me.mocha.economy.exception.NoAccountException
import me.mocha.economy.plugin
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.util.*

class YamlProvider : Provider {
    private val moneyFile = File(plugin.dataFolder, "money.yml")
    private val money: FileConfiguration = YamlConfiguration.loadConfiguration(moneyFile)

    override fun hasAccount(uuid: UUID): Boolean {
        return money.contains(uuid.toString())
    }

    override fun createAccount(uuid: UUID, default: Int): Boolean {
        if (!hasAccount(uuid)) {
            money[uuid.toString()] = default
            return true
        }
        return false
    }

    override fun getMoney(uuid: UUID): Int {
        if (hasAccount(uuid)) {
            return money.getInt(uuid.toString())
        } else {
            throw NoAccountException(uuid)
        }
    }

    override fun setMoney(uuid: UUID, amount: Int) {
        if (hasAccount(uuid)) {
            money[uuid.toString()] = amount
        } else {
            throw NoAccountException(uuid)
        }
    }

    override fun addMoney(uuid: UUID, amount: Int) {
        if (hasAccount(uuid)) {
            money[uuid.toString()] = getMoney(uuid) + amount
        } else {
            throw NoAccountException(uuid)
        }
    }

    override fun reduceMoney(uuid: UUID, amount: Int) {
        if (hasAccount(uuid)) {
            money[uuid.toString()] = getMoney(uuid) - amount
        } else {
            throw NoAccountException(uuid)
        }
    }

    override fun save() {
        money.save(moneyFile)
    }
}