package me.mocha.economy.command

import me.mocha.economy.EconomyManager
import me.mocha.economy.plugin
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import java.lang.Exception
import java.lang.NumberFormatException

class GiveMoneyTabCompleter : TabCompleter {
    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): MutableList<String> {
        return when (args.size) {
            1 -> plugin.server.onlinePlayers.map { it.name }.toMutableList().apply { add("@a") }.filter { it.startsWith(args[0]) }.toMutableList()
            else -> mutableListOf()
        }
    }
}

class GiveMoneyCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.size < 2) return false

        val amount = try {
            args[1].toInt()
        } catch (e: NumberFormatException) {
            return false
        }

        try {
            if (args[0] == "@a") {
                plugin.server.onlinePlayers.forEach { EconomyManager.addMoney(it, amount) }
                sender.sendMessage(plugin.getMessage("givemoney", "target" to "모든 플레이어", "amount" to amount, "unit" to EconomyManager.unit()))
            } else {
                plugin.server.getPlayerExact(args[0])?.let { player ->
                    EconomyManager.addMoney(player, amount)
                    sender.sendMessage(plugin.getMessage("givemoney", "target" to player.displayName, "amount" to amount, "unit" to EconomyManager.unit()))
                } ?: run {
                    sender.sendMessage(plugin.getMessage("noaccount", "player" to args[0]))
                }
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