package me.mocha.economy.command

import me.mocha.economy.EconomyManager
import me.mocha.economy.plugin
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.lang.Exception

class MyMoneyCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(plugin.getMessage("errors.onlyplayer"))
            return true
        }
        try {
            val money = EconomyManager.getMoney(sender)
            sender.sendMessage(plugin.getMessage("mymoney", "money" to money, "unit" to EconomyManager.unit()))
        } catch (e: Exception) {
            if (e.message?.startsWith(plugin.prefix()) == true) {
                sender.sendMessage(e.message!!)
            } else {
                sender.sendMessage(plugin.getMessage("errors.unknown"))
                e.printStackTrace()
            }
        }
        return true
    }

}