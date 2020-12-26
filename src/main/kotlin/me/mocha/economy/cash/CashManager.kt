package me.mocha.economy.cash

import me.mocha.economy.EconomyManager
import me.mocha.economy.exception.MoneyBelowZeroException
import me.mocha.economy.plugin
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import java.lang.Exception
import java.util.*

object CashManager {

    @Throws(MoneyBelowZeroException::class)
    fun publish(amount: Int): ItemStack {
        if (amount <= 0) throw MoneyBelowZeroException()

        return ItemStack(Material.PAPER, 1).apply {
            itemMeta = itemMeta.apply {
                setDisplayName("$amount${EconomyManager.unit()}")
                lore = listOf(
                    plugin.getMessage("cash.bankname"),
                    plugin.getMessage("cash.certification", "uid" to UUID.randomUUID())
                )
            }
        }
    }

    fun isCash(item: ItemStack): Boolean {
        if (item.hasItemMeta() && item.itemMeta.hasDisplayName() && item.itemMeta.hasLore()) {
            try {
                item.itemMeta.displayName.removeSuffix(EconomyManager.unit()).toInt()
                val line1 = item.itemMeta.lore?.get(0)
                val line2 = item.itemMeta.lore?.get(1)

                if (line1 != null && line1 == plugin.getMessage("cash.bankname") && line2 != null) {
                    return true
                }
            } catch (e: Exception) {
                return false
            }
        }
        return false
    }

    fun getMoney(item: ItemStack): Int {
        return if (isCash(item)) {
            val money = item.itemMeta.displayName.removeSuffix(EconomyManager.unit()).toInt()
            money * item.amount
        } else 0
    }

}