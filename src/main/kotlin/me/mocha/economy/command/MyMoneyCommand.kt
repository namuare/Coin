package me.mocha.economy.command

import me.mocha.economy.EconomyManager
import me.mocha.economy.exception.EconomyException
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
            val amount = EconomyManager.getMoney(sender)
            val unit = EconomyManager.unit()
            sender.sendMessage(plugin.getMessage("commands.mymoney", "amount" to amount, "unit" to unit))
        } catch (e: EconomyException) {
            sender.sendMessage(plugin.getMessage(e.messagePath))
        } catch (e: Exception) {
            sender.sendMessage(plugin.getMessage("errors.unknown"))
            e.printStackTrace()
        }
        return true
    }

}