package me.mocha.economy.command

import me.mocha.economy.EconomyManager
import me.mocha.economy.exception.EconomyException
import me.mocha.economy.plugin
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import java.lang.Exception

class SeeMoneyCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) return false

        try {
            plugin.server.getPlayerUniqueId(args[0])?.let { uuid ->
                val amount = EconomyManager.getMoney(uuid)
                val unit = EconomyManager.unit()
                sender.sendMessage(plugin.getMessage("commands.seemoney", "target" to args[0], "amount" to amount, "unit" to unit))
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