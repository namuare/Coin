package me.mocha.economy.command

import me.mocha.economy.EconomyManager
import me.mocha.economy.exception.EconomyException
import me.mocha.economy.plugin
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import java.lang.Exception
import java.lang.NumberFormatException

class GiveMoneyCommand : CommandExecutor {

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
                EconomyManager.addMoney(uuid, amount)
                sender.sendMessage(plugin.getMessage("commands.givemoney", "target" to args[0], "amount" to amount, "unit" to unit))

                plugin.server.getPlayer(uuid)?.sendMessage(plugin.getMessage("givedmoney", "issuer" to sender.name, "amount" to amount))
            } ?: run {
                sender.sendMessage(plugin.getMessage("errors.noaccount", "player" to args[0]))
            }
        } catch (e: EconomyException) {
            sender.sendMessage(plugin.getMessage(e.messagePath))
        } catch (e: Exception) {
            sender.sendMessage(plugin.getMessage("errors.unknown"))
            e.printStackTrace()
        }
        return true
    }

}