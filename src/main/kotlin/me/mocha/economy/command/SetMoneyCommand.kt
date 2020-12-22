package me.mocha.economy.command

import me.mocha.economy.EconomyManager
import me.mocha.economy.exception.EconomyException
import me.mocha.economy.plugin
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.lang.NumberFormatException

class SetMoneyTabCompleter : TabCompleter {
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): MutableList<String> {
        return when (args.size) {
            1 -> plugin.server.onlinePlayers.map { it.name }.toMutableList()
            else -> mutableListOf()
        }
    }
}

class SetMoneyCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.size < 2) return false

        val amount: Int
        try {
            amount = args[1].toInt()
        } catch (e: NumberFormatException) {
            return false
        }

        val player = plugin.server.getPlayerExact(args[0])

        if (player == null) {
            sender.sendMessage(plugin.getMessage("noaccount", "player" to args[0]))
            return true
        }

        try {
            EconomyManager.setMoney(player, amount)
            sender.sendMessage(
                plugin.getMessage(
                    "setmoney",
                    "target" to player.displayName, "amount" to amount, "unit" to EconomyManager.unit()
                )
            )
        } catch (e: EconomyException) {
            sender.sendMessage(e.message ?: plugin.getMessage("unknownerror"))
        } catch (e: IllegalArgumentException) {
            sender.sendMessage(e.message ?: plugin.getMessage("unknownerror"))
        } catch (e: Exception) {
            sender.sendMessage(plugin.getMessage("unknownerror"))
        }
        return true
    }

}