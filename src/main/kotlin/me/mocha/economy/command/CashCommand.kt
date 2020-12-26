package me.mocha.economy.command

import me.mocha.economy.EconomyManager
import me.mocha.economy.cash.CashManager
import me.mocha.economy.exception.EconomyException
import me.mocha.economy.plugin
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.lang.Exception

class CashCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) return false
        if (sender !is Player) {
            sender.sendMessage(plugin.getMessage("errors.onlyplayer"))
            return true
        }

        try {
            val amount = args[0].toInt()
            val cash = CashManager.publish(amount)
            val unit = EconomyManager.unit()

            EconomyManager.reduceMoney(sender, amount)
            sender.inventory.addItem(cash)
            sender.sendMessage(plugin.getMessage("cash.published", "money" to amount, "unit" to unit))
        } catch (e: EconomyException) {
            sender.sendMessage(plugin.getMessage(e.messagePath))
        } catch (e: Exception) {
            sender.sendMessage(plugin.getMessage("errors.unknown"))
            e.printStackTrace()
        }
        return true
    }

}