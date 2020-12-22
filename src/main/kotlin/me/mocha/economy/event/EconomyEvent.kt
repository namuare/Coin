package me.mocha.economy.event

import me.mocha.economy.plugin
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList
import org.bukkit.event.server.PluginEvent

private val handlers = HandlerList()

open class EconomyEvent : PluginEvent(plugin), Cancellable {

    private var cancelled: Boolean = false

    override fun isCancelled() = cancelled

    override fun setCancelled(cancel: Boolean) {
        this.cancelled = cancel
    }

    override fun getHandlers() = HANDLERS

    companion object {
        private val HANDLERS = HandlerList()

        @JvmStatic
        fun getHandlerList() = HANDLERS
    }

}