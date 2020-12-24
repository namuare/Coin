package me.mocha.economy.command

import me.mocha.economy.EconomyManager
import me.mocha.economy.plugin
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import java.lang.Exception
import java.lang.NumberFormatException

class TakeMoneyCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.size < 2) return false

        val amount = try {
            args[1].toInt()
        } catch (e: NumberFormatException) {
            return false
        }

        try {
            plugin.server.getPlayerExact(args[0])?.let { player ->
                EconomyManager.reduceMoney(player, amount)
                sender.sendMessage(plugin.getMessage("takemoney", "target" to player.displayName, "amount" to amount, "unit" to EconomyManager.unit()))
            } ?: run {
                sender.sendMessage(plugin.getMessage("noaccount", "player" to args[0]))
            }
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