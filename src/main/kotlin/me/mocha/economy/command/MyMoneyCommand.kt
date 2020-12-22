package me.mocha.economy.command

import me.mocha.economy.EconomyManager
import me.mocha.economy.exception.EconomyException
import me.mocha.economy.exception.NoAccountException
import me.mocha.economy.plugin
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.lang.Exception

class MyMoneyCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            try {
                val money = EconomyManager.getMoney(sender)
                sender.sendMessage(
                    plugin.getMessage("mymoney",
                    "money" to money,
                    "unit" to EconomyManager.unit())
                )
            } catch (e: EconomyException) {
                sender.sendMessage(e.message ?: plugin.getMessage("unknownerror"))
            } catch (e: Exception) {
                sender.sendMessage(plugin.getMessage("unknownerror"))
            }
        } else {
            sender.sendMessage(plugin.getMessage("onlyplayer"))
        }
        return true
    }

}