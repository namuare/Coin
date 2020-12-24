package me.mocha.economy

import me.mocha.economy.command.*
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
        getCommand("givemoney")?.setExecutor(GiveMoneyCommand())
        getCommand("setmoney")?.setExecutor(SetMoneyCommand())
        getCommand("takemoney")?.setExecutor(TakeMoneyCommand())
    }

    fun prefix() = config.getString("messages.prefix") ?: "[Economy]"

    fun getMessage(path: String, vararg replace: Pair<String, Any>): String {
        val prefix = prefix()
        var message = config.getString(if (path.startsWith("messages.")) path else "messages.${path}") ?: ""

        replace.forEach { message = message.replace("%${it.first}%", it.second.toString()) }

        return "$prefix $message"
    }

}