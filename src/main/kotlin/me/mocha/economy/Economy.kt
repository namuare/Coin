package me.mocha.economy

import me.mocha.economy.command.CashCommand
import me.mocha.economy.command.GiveMoneyCommand
import me.mocha.economy.command.GiveMoneyTabCompleter
import me.mocha.economy.command.MyMoneyCommand
import me.mocha.economy.provider.YamlProvider
import org.bukkit.plugin.java.JavaPlugin

lateinit var plugin: Economy

class Economy : JavaPlugin() {

    override fun onLoad() {
        dataFolder.mkdirs()
        saveDefaultConfig()
    }

    override fun onEnable() {
        server.pluginManager.registerEvents(EventListener, this)
        registerCommands()
        plugin = this
        EconomyManager.provider = YamlProvider()
    }

    override fun onDisable() {
        saveConfig()
    }

    private fun registerCommands() {
        getCommand("mymoney")?.setExecutor(MyMoneyCommand())
        getCommand("cash")?.setExecutor(CashCommand())
        getCommand("givemoney")?.let {
            it.setExecutor(GiveMoneyCommand())
            it.tabCompleter = GiveMoneyTabCompleter()
        }
    }

    fun getPrefix() = config.getString("messages.prefix")

    fun getMessage(path: String, vararg replace: Pair<String, Any>): String {
        val prefix = getPrefix()
        var message = config.getString(if (path.startsWith("messages.")) path else "messages.${path}") ?: ""

        replace.forEach { message = message.replace("%${it.first}%", it.second.toString()) }

        return "$prefix $message"
    }

}