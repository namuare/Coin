package me.mocha.economy.event

import me.mocha.economy.plugin
import org.bukkit.event.HandlerList
import org.bukkit.event.server.PluginEvent

open class EconomyEvent : PluginEvent(plugin) {

    override fun getHandlers() = HANDLERS

    companion object {
        private val HANDLERS = HandlerList()

        @JvmStatic
        fun getHandlerList() = HANDLERS
    }

}