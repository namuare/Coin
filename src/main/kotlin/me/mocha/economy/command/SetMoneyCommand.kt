package me.mocha.economy.command

import me.mocha.economy.EconomyManager
import me.mocha.economy.plugin
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import java.lang.Exception
import java.lang.NumberFormatException

class SetMoneyTabCompleter : TabCompleter {
    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): MutableList<String> {
        return when (args.size) {
            1 -> plugin.server.offlinePlayers.filter { it.name?.startsWith(args[0]) ?: false }.map { it.name!! }.toMutableList()
            else -> mutableListOf()
        }
    }
}

class SetMoneyCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.size < 2) return false

        val amount = try {
            args[1].toInt()
        } catch (e: NumberFormatException) {
            return false
        }

        try {
            plugin.server.getPlayerUniqueId(args[0])?.let { uuid ->
                val unit = EconomyManager.unit()
                EconomyManager.setMoney(uuid, amount)
                sender.sendMessage(plugin.getMessage("commands.setmoney", "target" to args[0], "amount" to amount, "unit" to unit))
                plugin.server.getPlayer(uuid)?.sendMessage(plugin.getMessage("settedmoney", "issuer" to sender.name, "amount" to amount))
            } ?: run {
                sender.sendMessage(plugin.getMessage("errors.noaccount", "player" to args[0]))
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