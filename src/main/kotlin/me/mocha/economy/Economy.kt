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
        EconomyManager.save()
    }

    private fun registerCommands() {
        getCommand("mymoney")?.setExecutor(MyMoneyCommand())
        getCommand("seemoney")?.apply {
            setExecutor(SeeMoneyCommand())
            tabCompleter = SeeMoneyTabCompleter()
        }
        getCommand("cash")?.setExecutor(CashCommand())
        getCommand("setmoney")?.apply {
            setExecutor(SetMoneyCommand())
            tabCompleter = SetMoneyTabCompleter()
        }
        getCommand("givemoney")?.apply {
            setExecutor(GiveMoneyCommand())
            tabCompleter = GiveMoneyTabCompleter()
        }
        getCommand("takemoney")?.apply {
            setExecutor(TakeMoneyCommand())
            tabCompleter = TakeMoneyTabCompleter()
        }
    }

    fun prefix() = config.getString("messages.prefix") ?: "[Economy]"

    fun getMessage(path: String, vararg replace: Pair<String, Any>): String {
        val prefix = prefix()
        var message = config.getString(if (path.startsWith("messages.")) path else "messages.${path}") ?: ""

        replace.forEach { message = message.replace("%${it.first}%", it.second.toString()) }

        return "$prefix $message"
    }

}